package me.inassar.feature.feed.data.remote.dto.feed.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
/**
 * Minimal DTO mirroring the dummy feed API response structure.
 *
 * @property method Echoed HTTP method (e.g., GET).
 * @property status Request status string (e.g., ok).
 */
class FeedResponseDto(
    @SerialName("method")
    val method: String? = null,
    @SerialName("status")
    val status: String? = null
)
