package me.inassar.feature.feed.presentation.manipulator

import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

/**
 * UI State that represents FeedScreen
 **/
data class FeedState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: String? = null,
    val capabilities: DeviceCapabilities = DeviceCapabilities(
        platform = PlatformEnum.DEFAULT,
        supportsLocalCache = false
    )
)