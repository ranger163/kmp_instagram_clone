package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

/** Capability provider for Wasm/JS environments. */
class WasmJsCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities =
        DeviceCapabilities(
            platform = PlatformEnum.WASM_JS,
            supportsLocalCache = false
        )
}

/**
 * Dispatcher provider for Wasm/JS targets (all map to `Dispatchers.Default`).
 */
class WasmJsDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val main = Dispatchers.Default
}
