package me.inassar.core.cache.feature.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert
    suspend fun insertFeed(feedEntity: FeedEntity)

    @Query("select * from FeedEntity")
    fun getFeed(): Flow<FeedEntity?>

    @Query("delete from FeedEntity where id >= 1")
    suspend fun deleteFeed()
}