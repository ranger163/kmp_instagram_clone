package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

/**
 * Android implementation describing capabilities available on the device.
 */
class AndroidCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities =
        DeviceCapabilities(
            platform = PlatformEnum.ANDROID,
            supportsLocalCache = true
        )
}

/**
 * Provides Android-specific coroutine dispatchers sourced from `Dispatchers`.
 */
class AndroidDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main
}
