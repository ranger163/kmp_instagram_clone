package me.inassar.feature.feed.data.cache

import kotlinx.coroutines.flow.Flow
import me.inassar.core.cache.db.Feed

interface FeedCache {
    suspend fun insertFeed(feedEntity: Feed)
     fun getFeed(): Flow<Feed?>
    suspend fun deleteFeed()
}