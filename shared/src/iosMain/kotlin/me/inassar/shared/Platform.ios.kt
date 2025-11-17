package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

class IosCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities =
        DeviceCapabilities(
            platform = PlatformEnum.IOS,
            supportsLocalCache = true
        )
}

class IosDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main

}