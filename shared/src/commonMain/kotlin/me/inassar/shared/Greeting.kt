package me.inassar.shared

import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

class Greeting {
    private val platform = DeviceCapabilities(
        platform = PlatformEnum.SERVER,
        supportsLocalCache = false
    ).platform

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}