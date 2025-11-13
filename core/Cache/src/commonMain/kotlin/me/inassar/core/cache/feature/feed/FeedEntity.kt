package me.inassar.core.cache.feature.feed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FeedEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val method: String,
    val status: String
)