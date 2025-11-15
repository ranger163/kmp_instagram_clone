package me.inassar.feature.feed.data.repository

import kotlinx.coroutines.flow.firstOrNull
import me.inassar.feature.feed.data.cache.FeedCache
import me.inassar.feature.feed.data.mapper.toDomain
import me.inassar.feature.feed.data.mapper.toEntity
import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.domain.mapper.toDomain
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.shared.isJsPlatform

class FeedRepositoryImpl(
    private val remote: FeedRemoteApi,
    private val cache: FeedCache
) : FeedRepository {
    override suspend fun retrieveLocalFeed(): Result<DomainFeed> {
        val feed = cache.getFeed().firstOrNull()
        return if (feed != null) Result.success(feed.toDomain())
        else Result.failure(Exception("No data found in cache"))
    }

    override suspend fun retrieveFeed(): Result<DomainFeed> {

        return when (isJsPlatform()) {
            true -> pingBackend()
            false -> {
                // 1) Try cache first
                println("Getting data from cache")
                cache.getFeed().firstOrNull()?.let { cached ->
                    return Result.success(cached.toDomain())
                }

                println("Cache empty, fetching from remote")
                // 2) Otherwise fetch from remote, write-through to cache, then return from cache
                return remote.pingBackend().mapCatching { dto ->
                    cache.insertFeed(dto.toEntity())
                    val fresh = cache.getFeed().firstOrNull()
                    requireNotNull(fresh) { "Cache write failed: no data after remote fetch" }
                    fresh.toDomain()
                }
            }
        }

    }

    override suspend fun deleteLocalFeed() {
        cache.deleteFeed()
    }

    private suspend fun pingBackend() = remote.pingBackend().mapCatching { it.toDomain() }
}