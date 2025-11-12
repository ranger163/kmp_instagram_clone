package me.inassar.feature.feed.data.repository

import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.domain.mapper.toDomain
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.domain.repository.FeedRepository

class FeedRepositoryImpl(private val remote: FeedRemoteApi) : FeedRepository {
    /**
     * Example call mirroring: curl --location 'https://dummyjson.com/test'
     */
    override suspend fun pingBackend(): Result<DomainFeed> {
        return remote.pingBackend().mapCatching { responseDto ->
            responseDto.toDomain()
        }
    }
}