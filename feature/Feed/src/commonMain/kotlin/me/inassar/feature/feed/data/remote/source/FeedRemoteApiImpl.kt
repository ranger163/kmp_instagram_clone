package me.inassar.feature.feed.data.remote.source

import me.inassar.core.network.NetworkClient
import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto

class FeedRemoteApiImpl(private val client: NetworkClient) : FeedRemoteApi {
    /**
     * Example call mirroring: curl --location 'https://dummyjson.com/test'
     */
    override suspend fun pingBackend(): Result<FeedResponseDto> =
        client.getSafe<FeedResponseDto>("/test")
}