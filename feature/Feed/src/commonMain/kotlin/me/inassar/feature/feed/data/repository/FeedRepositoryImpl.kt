package me.inassar.feature.feed.data.repository

import kotlinx.coroutines.flow.firstOrNull
import me.inassar.feature.feed.data.cache.FeedCache
import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.domain.mapper.toCacheParams
import me.inassar.feature.feed.domain.mapper.toDomain
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.logger

/**
 * Concrete [FeedRepository] that orchestrates remote and cached data sources.
 *
 * @property remote Network API used for fetching the feed.
 * @property cache Local persistence layer storing the latest feed.
 * @property platformCapabilities Provides platform traits to decide caching behavior.
 */
class FeedRepositoryImpl(
    private val remote: FeedRemoteApi,
    private val cache: FeedCache,
    private val platformCapabilities: PlatformCapabilitiesProvider,
) : FeedRepository {


    /** Chooses between cached and remote sources based on platform support. */
    override suspend fun retrieveFeed(): Result<DomainFeed> =
        when (platformCapabilities.getCapabilities().supportsLocalCache) {
            false -> pingBackend()
            true -> {
                logger("FeedRepositoryImpl:retrieveFeed").i("Getting data from cache")
                retrieveLocalFeed().onSuccess { return Result.success(it) }

                logger("FeedRepositoryImpl:retrieveFeed").i("Cache empty, fetching from remote")
                remote.pingBackend().mapCatching { dto ->
                    val (method, status) = dto.toCacheParams()
                    cache.insertFeed(method = method, status = status)
                    val fresh = cache.getFeed().firstOrNull()
                    requireNotNull(fresh) { "Cache write failed: no data after remote fetch" }
                    fresh.toDomain(fromCache = false)
                }
            }
        }

    /** Clears cached feed data regardless of platform support. */
    override suspend fun deleteLocalFeed() {
        cache.deleteFeed()
    }

    /** Attempts to read the cached feed entry, returning a [Result]. */
    private suspend fun retrieveLocalFeed(): Result<DomainFeed> =
        when (val feed = cache.getFeed().firstOrNull()) {
            null -> Result.failure(Exception("No data found in cache"))
            else -> Result.success(feed.toDomain(fromCache = true))
        }

    /** Performs the remote call and maps the DTO into the domain model. */
    private suspend fun pingBackend() =
        remote.pingBackend().mapCatching { it.toDomain() }
}
