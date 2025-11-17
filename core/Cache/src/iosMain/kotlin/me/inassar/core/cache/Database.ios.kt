package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS SQLDelight driver factory backed by Apple's native SQLite implementation.
 */
class IosDatabaseDriver : DatabaseDriverFactory {

    /**
     * Creates the [SqlDriver] using `NativeSqliteDriver`.
     */
    override fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "database.db"
        )
}

/**
 * Binds the iOS database driver implementation into Koin.
 */
actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { IosDatabaseDriver() }
    }
