package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

class JsCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities = DeviceCapabilities(
        platform = PlatformEnum.JS,
        supportsLocalCache = false
    )
}

class JsDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val main = Dispatchers.Default

}