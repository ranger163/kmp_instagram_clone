package me.inassar.feature.feed.domain.repository

import me.inassar.feature.feed.domain.model.DomainFeed

interface FeedRepository {
    suspend fun pingBackend(): Result<DomainFeed>
}