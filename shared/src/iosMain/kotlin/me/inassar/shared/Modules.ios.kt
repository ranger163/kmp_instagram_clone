package me.inassar.shared

import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import org.koin.core.module.Module
import org.koin.dsl.module


/** iOS binding for [PlatformCapabilitiesProvider]. */
actual val platformCapabilitiesProviderModule: Module
    get() = module {
        single<PlatformCapabilitiesProvider> { IosCapabilitiesProvider() }
    }

/** iOS binding for [DispatcherProvider]. */
actual val dispatcherProviderModule: Module
    get() = module { single<DispatcherProvider> { IosDispatcherProvider() } }
