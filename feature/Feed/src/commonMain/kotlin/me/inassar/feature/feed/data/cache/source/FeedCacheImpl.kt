package me.inassar.feature.feed.data.cache.source

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import me.inassar.core.cache.db.Feed
import me.inassar.core.cache.db.FeedQueries
import me.inassar.feature.feed.data.cache.FeedCache
import me.inassar.shared.helpers.DispatcherProvider

/**
 * SQLDelight-backed [FeedCache] implementation.
 *
 * @property queries Generated SQLDelight queries for the feed table.
 * @property dispatcher Dispatcher provider used to collect flows on the default dispatcher.
 */
class FeedCacheImpl(
    private val queries: FeedQueries,
    private val dispatcher: DispatcherProvider
) : FeedCache {
    /** Stores/overwrites the feed entry inside the local database. */
    override suspend fun insertFeed(method: String, status: String) {
        queries.insertFeed(method = method, status = status)
    }

    /** Emits the most recent feed entry as a hot SQLDelight flow. */
    override fun getFeed(): Flow<Feed?> =
        queries.selectLatestFeed().asFlow().mapToOneOrNull(dispatcher.default)

    /** Deletes all feed entries from the cache. */
    override suspend fun deleteFeed() {
        queries.deleteAllFeed()
    }
}
