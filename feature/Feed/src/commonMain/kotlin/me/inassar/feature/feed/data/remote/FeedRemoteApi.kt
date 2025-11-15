package me.inassar.feature.feed.data.remote

import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto

interface FeedRemoteApi {
    suspend fun pingBackend(): Result<FeedResponseDto>
}