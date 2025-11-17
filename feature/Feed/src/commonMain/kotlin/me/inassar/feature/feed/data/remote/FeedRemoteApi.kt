package me.inassar.feature.feed.data.remote

import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto

/**
 * Defines remote feed endpoints consumed by the repository.
 */
interface FeedRemoteApi {
    /** Performs a sample request to fetch the feed response payload. */
    suspend fun pingBackend(): Result<FeedResponseDto>
}
