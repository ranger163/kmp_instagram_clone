package me.inassar.feature.feed.data.cache

import kotlinx.coroutines.flow.Flow
import me.inassar.core.cache.feature.feed.FeedEntity

interface FeedCache {
    suspend fun insertFeed(feedEntity: FeedEntity)
     fun getFeed(): Flow<FeedEntity?>
    suspend fun deleteFeed()
}