/**
 * Multiplatform networking utilities built on top of Ktor's `HttpClient`.
 *
 * This file provides:
 * - `NetworkConfig` — a single immutable configuration to standardize base URL, timeouts,
 *   retries, logging, and JSON across platforms.
 * - `NetworkClient` — a thin wrapper exposing typed, "safe" request helpers that return
 *   `Result<T>` to keep failures isolated in data/domain layers.
 *
 * Key features
 * - Content negotiation via kotlinx.serialization `Json`.
 * - Opt-in logging with configurable levels and logger.
 * - Sensible timeouts for request/connect/socket phases.
 * - Exponential backoff retries for transient failures.
 * - Ability to override the base URL per call by passing an absolute URL.
 */
package me.inassar.core.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

/**
 * Default set of HTTP status codes that are considered transient and therefore retriable.
 */
private val DefaultRetryStatusCodes: Set<HttpStatusCode> = setOf(
    HttpStatusCode.RequestTimeout,
    HttpStatusCode.TooManyRequests,
    HttpStatusCode.BadGateway,
    HttpStatusCode.ServiceUnavailable,
    HttpStatusCode.GatewayTimeout
)

/**
 * Default JSON configuration shared across client instances.
 */
private val defaultJson: Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
    explicitNulls = false
}

/**
 * Immutable configuration object that drives how the underlying Ktor [HttpClient] behaves.
 *
 * Use this object to centralize environment-specific concerns (base URL, timeouts, logging,
 * retries, and JSON configuration) while keeping production defaults sensible. Most projects
 * can create a single instance per environment and inject it where needed.
 *
 * Notes
 * - `baseUrl` is applied via Ktor's `DefaultRequest` and can be overridden by passing a full
 *   absolute URL to any of the `*Safe` request functions.
 * - Retries use an exponential backoff strategy. Only the status codes included in
 *   [retryStatusCodes] (or exceptions allowed by [shouldRetryOnException]) will be retried.
 * - Set [expectSuccess] to `true` if you prefer Ktor to throw for 4xx/5xx responses.
 *
 * @property baseUrl Base URL used for all requests unless a fully qualified URL is provided
 *                   in the request call (e.g., `https://api.example.com/users`).
 * @property enableLogging Enables Ktor's [Logging] plugin when `true`.
 * @property logLevel Logging level used by the [Logging] plugin (default: [LogLevel.INFO]).
 * @property expectSuccess When `true`, Ktor throws for non-2xx responses.
 * @property defaultHeaders Headers applied to every request by default (e.g., auth, app ID).
 * @property requestTimeoutMillis Maximum total time for a request (in ms).
 * @property connectTimeoutMillis Maximum time to establish a TCP connection (in ms).
 * @property socketTimeoutMillis Maximum inactivity time between packets (in ms).
 * @property maxRetries Maximum number of retry attempts for eligible failures.
 * @property retryDelayMillis Base delay (in ms) used for exponential backoff.
 * @property retryStatusCodes HTTP status codes that should be considered retriable.
 * @property shouldRetryOnException Predicate that decides whether a thrown [Throwable]
 *                                  is transient and should be retried (default: only IO issues).
 * @property json Shared kotlinx.serialization [Json] instance used by ContentNegotiation.
 * @property logger Logger implementation used when [enableLogging] is `true`.
 */
data class NetworkConfig(
    val baseUrl: String,
    val enableLogging: Boolean = false,
    val logLevel: LogLevel = LogLevel.INFO,
    val expectSuccess: Boolean = false,
    val defaultHeaders: Map<String, String> = emptyMap(),
    val requestTimeoutMillis: Long = 30_000,
    val connectTimeoutMillis: Long = 15_000,
    val socketTimeoutMillis: Long = 30_000,
    val maxRetries: Int = 3,
    val retryDelayMillis: Long = 1_000,
    val retryStatusCodes: Set<HttpStatusCode> = DefaultRetryStatusCodes,
    val shouldRetryOnException: (Throwable) -> Boolean = { it is IOException },
    val json: Json = defaultJson,
    val logger: Logger = Logger.DEFAULT,
)

/**
 * Thin, production-focused wrapper around Ktor's [HttpClient].
 *
 * The client exposes typed request helpers and "safe" variants that return [Result], allowing
 * call sites to handle successes/failures without throwing. Prefer the `*Safe` functions in
 * data/domain layers to keep error handling explicit and localized.
 *
 * @property httpClient The underlying Ktor client. You rarely need to access it directly; the
 *                      wrapper methods provide a typed, consistent surface.
 */
class NetworkClient internal constructor(val httpClient: HttpClient) {

    /**
     * Executes an HTTP GET request and returns a [Result] containing the decoded body of type [T].
     *
     * Notes
     * - If [endpoint] is a relative path (e.g., "/users"), the configured [NetworkConfig.baseUrl]
     *   is used as the base. If it's an absolute URL, it overrides the configured base URL.
     * - Response body is deserialized using the client's configured `ContentNegotiation`/`Json`.
     * - Retries and timeouts are applied according to [NetworkConfig].
     * - Cancellation propagates to the underlying Ktor call when the calling coroutine is canceled.
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param block Optional lambda to further configure the [HttpRequestBuilder] (headers, params, etc.).
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> getSafe(
        endpoint: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching { httpClient.get(urlString = endpoint) { block() }.body<T>() }

    /**
     * Executes an HTTP POST request and returns a [Result] containing the decoded body of type [T].
     *
     * Notes
     * - If [endpoint] is a relative path, the configured [NetworkConfig.baseUrl] is used; an
     *   absolute URL overrides it.
     * - Use [block] to set headers, query params, or a request body (e.g., `setBody(payload)`).
     * - Serialization is handled by the installed `ContentNegotiation`/`Json` plugin.
     * - Retries/timeouts are applied per [NetworkConfig]; coroutine cancellation is propagated.
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param block Optional lambda to tweak the [HttpRequestBuilder], including `setBody`.
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> postSafe(
        endpoint: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching { httpClient.post(urlString = endpoint) { block() }.body<T>() }

    /**
     * Executes an HTTP PUT request and returns a [Result] containing the decoded body of type [T].
     *
     * Typical usage is to update or replace a resource. Set a body via `setBody(payload)` inside [block].
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param block Optional lambda to configure the [HttpRequestBuilder] (headers, query, `setBody`).
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> putSafe(
        endpoint: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching { httpClient.put(urlString = endpoint) { block() }.body<T>() }

    /**
     * Executes an HTTP PATCH request and returns a [Result] containing the decoded body of type [T].
     *
     * Use this for partial updates. Provide the payload via `setBody(payload)` inside [block].
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param block Optional lambda to configure the [HttpRequestBuilder] (headers, query, `setBody`).
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> patchSafe(
        endpoint: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching { httpClient.patch(urlString = endpoint) { block() }.body<T>() }

    /**
     * Executes an HTTP DELETE request and returns a [Result] containing the decoded body of type [T].
     *
     * Some APIs return a representation of the deleted resource; others return an empty body.
     * If nobody is returned, consider using `Result<Unit>` as [T].
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param block Optional lambda to configure the [HttpRequestBuilder] (headers, query params).
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> deleteSafe(
        endpoint: String,
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching { httpClient.delete(urlString = endpoint) { block() }.body<T>() }

    /**
     * Executes a request with an explicit HTTP [method] and returns a [Result] of [T].
     *
     * This is the flexible, low-level helper behind the verb-specific functions.
     *
     * @param endpoint Relative path ("/path") or absolute URL ("https://host/path").
     * @param method HTTP method to use (e.g., [HttpMethod.Get], [HttpMethod.Post]).
     * @param block Optional lambda to configure the [HttpRequestBuilder] (headers, query, `setBody`).
     * @return [Result] wrapping the decoded response body of type [T] or the failure.
     */
    suspend inline fun <reified T> requestSafe(
        endpoint: String,
        method: HttpMethod,
        noinline block: HttpRequestBuilder.() -> Unit = {},
    ): Result<T> = runCatching {
        httpClient.request(urlString = endpoint) {
            this.method = method
            block()
        }.body<T>()
    }

    /**
     * Closes the underlying [HttpClient].
     *
     * Call this when the client lifecycle ends (e.g., application shutdown) to
     * release resources and cancel in-flight requests.
     *
     * This is idempotent; closing an already closed client is a no-op.
     */
    fun close() = httpClient.close()

    companion object {
        /**
         * Factory function that builds a fully configured multiplatform [HttpClient] and wraps it
         * into a [NetworkClient]. Production defaults are applied, while allowing environment-specific
         * overrides via [config].
         *
         * Installed plugins and mappings
         * - DefaultRequest: applies [NetworkConfig.baseUrl] and [NetworkConfig.defaultHeaders].
         * - ContentNegotiation: uses [NetworkConfig.json] for kotlinx.serialization.
         * - HttpTimeout: uses [NetworkConfig.requestTimeoutMillis], [NetworkConfig.connectTimeoutMillis],
         *   and [NetworkConfig.socketTimeoutMillis].
         * - HttpRequestRetry: enabled when [NetworkConfig.maxRetries] > 0 with exponential backoff
         *   based on [NetworkConfig.retryDelayMillis] and [NetworkConfig.retryStatusCodes].
         * - Logging: enabled when [NetworkConfig.enableLogging] with [NetworkConfig.logLevel] and [NetworkConfig.logger].
         *
         * @param engine Platform-specific [HttpClientEngine] provided by `platformClientEngine()`.
         * @param config Immutable configuration controlling all client behavior.
         * @return A [NetworkClient] instance ready for use across the app.
         */
        internal fun create(
            engine: HttpClientEngine,
            config: NetworkConfig
        ): NetworkClient {
            val client = HttpClient(engine) {
                // Bubble up server errors only if you opted in via config.
                expectSuccess = config.expectSuccess

                // Default request configuration (base URL, headers)
                install(DefaultRequest) {
                    url { takeFrom(config.baseUrl) }
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                    config.defaultHeaders.forEach { (k, v) -> header(k, v) }
                }

                // JSON serialization
                install(ContentNegotiation) { json(config.json) }

                // Timeouts
                install(HttpTimeout) {
                    requestTimeoutMillis = config.requestTimeoutMillis
                    connectTimeoutMillis = config.connectTimeoutMillis
                    socketTimeoutMillis = config.socketTimeoutMillis
                }

                // Retries with exponential backoff
                if (config.maxRetries > 0) {
                    install(HttpRequestRetry) {
                        retryIf(maxRetries = config.maxRetries) { _, response ->
                            response.status in config.retryStatusCodes
                        }
                        retryOnExceptionIf(maxRetries = config.maxRetries) { _, cause ->
                            config.shouldRetryOnException(cause)
                        }
                        exponentialDelay(baseDelayMs = config.retryDelayMillis)
                    }
                }

                // Logging (do not log bodies in production unless necessary)
                if (config.enableLogging) {
                    install(Logging) {
                        logger = config.logger
                        level = config.logLevel
                    }
                }
            }

            return NetworkClient(client)
        }
    }

}
