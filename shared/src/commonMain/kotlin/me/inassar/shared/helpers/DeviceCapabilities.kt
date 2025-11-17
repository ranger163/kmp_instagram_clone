package me.inassar.shared.helpers

interface PlatformCapabilitiesProvider {
    fun getCapabilities(): DeviceCapabilities
}

data class DeviceCapabilities(
    val platform: PlatformEnum,
    val supportsLocalCache: Boolean
)
