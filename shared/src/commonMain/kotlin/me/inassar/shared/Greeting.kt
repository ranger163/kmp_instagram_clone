package me.inassar.shared

import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

/**
 * Sample greeting helper demonstrating multiplatform capability detection.
 */
class Greeting {
    /**
     * Captures the resolved platform using the shared `DeviceCapabilities` helper.
     */
    private val platform = DeviceCapabilities(
        platform = PlatformEnum.SERVER,
        supportsLocalCache = false
    ).platform

    /**
     * Returns a friendly greeting message referencing the active platform name.
     */
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
