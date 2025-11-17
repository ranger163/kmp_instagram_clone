package me.inassar.feature.feed.data.cache.source

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import me.inassar.core.cache.db.Feed
import me.inassar.core.cache.db.FeedQueries
import me.inassar.feature.feed.data.cache.FeedCache
import me.inassar.shared.helpers.DispatcherProvider

class FeedCacheImpl(
    private val queries: FeedQueries,
    private val dispatcher: DispatcherProvider
) : FeedCache {
    override suspend fun insertFeed(method: String, status: String) {
        queries.insertFeed(method = method, status = status)
    }

    override fun getFeed(): Flow<Feed?> =
        queries.selectLatestFeed().asFlow().mapToOneOrNull(dispatcher.default)

    override suspend fun deleteFeed() {
        queries.deleteAllFeed()
    }
}