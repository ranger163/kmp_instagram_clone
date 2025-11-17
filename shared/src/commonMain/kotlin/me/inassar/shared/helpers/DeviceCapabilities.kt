package me.inassar.shared.helpers

/**
 * Contract for producing platform capability metadata (cache, env, etc.).
 */
interface PlatformCapabilitiesProvider {
    /** Returns the computed [DeviceCapabilities] for the current runtime. */
    fun getCapabilities(): DeviceCapabilities
}

/**
 * Immutable snapshot of the platform's capabilities.
 *
 * @property platform The resolved platform identity.
 * @property supportsLocalCache Whether persisting local data is supported.
 */
data class DeviceCapabilities(
    val platform: PlatformEnum,
    val supportsLocalCache: Boolean
)
