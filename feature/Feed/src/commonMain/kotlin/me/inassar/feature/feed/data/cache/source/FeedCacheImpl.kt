package me.inassar.feature.feed.data.cache.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import me.inassar.core.cache.feature.feed.FeedDao
import me.inassar.core.cache.feature.feed.FeedEntity
import me.inassar.feature.feed.data.cache.FeedCache

class FeedCacheImpl(private val dao: FeedDao) : FeedCache {
    override suspend fun insertFeed(feedEntity: FeedEntity) {
        dao.insertFeed(feedEntity)
    }

    override fun getFeed(): Flow<FeedEntity?> = flow {
        emit(dao.getFeed().firstOrNull())
    }

    override suspend fun deleteFeed() {
        dao.deleteFeed()
    }
}