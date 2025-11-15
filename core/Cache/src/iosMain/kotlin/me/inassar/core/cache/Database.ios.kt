package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


class IosDatabaseDriver : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver = NativeSqliteDriver(
    schema = AppDatabase.Schema,
    name = "database.db"
    )
}

actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { IosDatabaseDriver() }
    }