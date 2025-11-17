package me.inassar.core.cache.di

import me.inassar.core.cache.DatabaseDriverFactory
import me.inassar.core.cache.db.AppDatabase
import me.inassar.core.cache.platformCacheModule
import org.koin.dsl.module

/**
 * Shared cache module registering SQLDelight database and feed queries across platforms.
 */
val cacheModule = module {
    includes(platformCacheModule)
    single {
        // create SQLDelight driver per platform
        val driverFactory: DatabaseDriverFactory = get()
        AppDatabase(driverFactory.createDriver())
    }

    // expose FeedQueries
    single { get<AppDatabase>().feedQueries }
}
