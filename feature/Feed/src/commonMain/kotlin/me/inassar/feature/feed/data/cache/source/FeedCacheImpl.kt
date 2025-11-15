package me.inassar.feature.feed.data.cache.source

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.inassar.core.cache.db.Feed
import me.inassar.core.cache.db.FeedQueries
import me.inassar.feature.feed.data.cache.FeedCache

class FeedCacheImpl(private val queries: FeedQueries) : FeedCache {
    override suspend fun insertFeed(feedEntity: Feed) {
        queries.insertFeed(method = feedEntity.method, status = feedEntity.status)
    }

    override fun getFeed(): Flow<Feed?> = flow {
        emit(queries.selectLatestFeed().asFlow().map { query -> query.executeAsOneOrNull() }.firstOrNull())
    }

    override suspend fun deleteFeed() {
        queries.deleteAllFeed()
    }
}