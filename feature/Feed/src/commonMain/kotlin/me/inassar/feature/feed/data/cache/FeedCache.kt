package me.inassar.feature.feed.data.cache

import kotlinx.coroutines.flow.Flow
import me.inassar.core.cache.db.Feed

/**
 * Contract for persisting and retrieving feed records from the local cache.
 */
interface FeedCache {
    /** Persists the last fetched feed metadata. */
    suspend fun insertFeed(method: String, status: String)

    /** Emits the cached feed entry, if any. */
    fun getFeed(): Flow<Feed?>

    /** Clears the cached feed entry. */
    suspend fun deleteFeed()
}
