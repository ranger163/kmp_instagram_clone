package me.inassar.shared

import kotlinx.coroutines.Dispatchers
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import me.inassar.shared.helpers.PlatformEnum

/**
 * Desktop/JVM capability provider describing host traits.
 */
class JvmCapabilitiesProvider : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities = DeviceCapabilities(
        platform = PlatformEnum.DESKTOP,
        supportsLocalCache = true
    )
}

/**
 * Coroutine dispatchers used on JVM (Desktop/Server) targets.
 */
class JvmDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main = Dispatchers.Default
}
