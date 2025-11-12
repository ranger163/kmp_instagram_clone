package me.inassar.feature.feed.data.remote.dto.feed.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
{ status: 'ok', method: 'GET' }
 */
@Serializable
class FeedResponseDto(
    @SerialName("method")
    val method: String? = null, // GET
    @SerialName("status")
    val status: String? = null // ok
)