package me.inassar.shared.di

import me.inassar.shared.dispatcherProviderModule
import me.inassar.shared.platformCapabilitiesProviderModule
import org.koin.dsl.module

/**
 * Aggregates platform-aware capability and dispatcher bindings.
 */
val sharedModule = module {
    includes(platformCapabilitiesProviderModule)
    includes(dispatcherProviderModule)
}
