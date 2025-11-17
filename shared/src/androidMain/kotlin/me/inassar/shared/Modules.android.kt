package me.inassar.shared

import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider
import org.koin.core.module.Module
import org.koin.dsl.module


/** Android binding for [PlatformCapabilitiesProvider]. */
actual val platformCapabilitiesProviderModule: Module
    get() = module {
        single<PlatformCapabilitiesProvider> { AndroidCapabilitiesProvider() }
    }

/** Android binding for [DispatcherProvider]. */
actual val dispatcherProviderModule: Module
    get() = module { single<DispatcherProvider> { AndroidDispatcherProvider() } }
