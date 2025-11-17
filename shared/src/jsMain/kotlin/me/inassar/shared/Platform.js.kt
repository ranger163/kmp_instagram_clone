package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

/**
 * JavaScript capability provider describing the browser/runtime traits.
 */
class JsCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities = DeviceCapabilities(
        platform = PlatformEnum.JS,
        supportsLocalCache = false
    )
}

/**
 * Provides dispatchers for JS targets (all map to `Dispatchers.Default`).
 */
class JsDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val main = Dispatchers.Default
}
