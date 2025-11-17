package me.inassar.feature.feed.presentation.manipulator

import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

/**
 * UI state for the feed screen.
 *
 * @property isLoading Indicates whether a network/cache request is running.
 * @property error Optional error message shown to the user.
 * @property data Renderable feed payload.
 * @property capabilities Platform capabilities used to toggle UI affordances.
 */
data class FeedState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: String? = null,
    val capabilities: DeviceCapabilities = DeviceCapabilities(
        platform = PlatformEnum.DEFAULT,
        supportsLocalCache = false
    )
)
