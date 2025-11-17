package me.inassar.feature.feed.domain.repository

import me.inassar.feature.feed.domain.model.DomainFeed

interface FeedRepository {
    suspend fun retrieveFeed(): Result<DomainFeed>
    suspend fun deleteLocalFeed()
}