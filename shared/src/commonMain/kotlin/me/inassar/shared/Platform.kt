package me.inassar.shared

import org.koin.core.module.Module

/**
 * Platform-specific module that binds the [PlatformCapabilitiesProvider].
 */
expect val platformCapabilitiesProviderModule: Module

/**
 * Platform-specific module that exposes a [DispatcherProvider].
 */
expect val dispatcherProviderModule: Module
