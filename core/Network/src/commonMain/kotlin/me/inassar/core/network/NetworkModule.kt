package me.inassar.core.network

import io.ktor.client.engine.*
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides the platform-specific Ktor [HttpClientEngine].
 *
 * Each target supplies its own `actual` implementation selecting the recommended engine
 * for that platform (e.g., OkHttp on Android, CIO on JVM, Darwin on iOS, Js on JS/wasm).
 */
internal expect fun platformClientEngine(): HttpClientEngine

/**
 * Koin module that wires a singleton [NetworkClient] using the platform engine and the provided [NetworkConfig].
 *
 * Usage
 * - Provide an environment-specific [NetworkConfig] from your app's bootstrap code and load this module.
 * - The created client is registered as a singleton and can be injected where needed.
 *
 * @param config Immutable network configuration used to build the underlying Ktor client.
 */
fun networkModule(config: NetworkConfig): Module = module {
    single { NetworkClient.create(engine = platformClientEngine(), config = config) }
}
