package me.inassar.feature.feed.domain.repository

import me.inassar.feature.feed.domain.model.DomainFeed

/**
 * Abstracts feed retrieval and cache mutation behaviors for the domain layer.
 */
interface FeedRepository {
    /** Fetches the feed, preferring network but falling back to cache. */
    suspend fun retrieveFeed(): Result<DomainFeed>

    /** Purges the cached feed data. */
    suspend fun deleteLocalFeed()
}
