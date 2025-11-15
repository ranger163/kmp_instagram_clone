package me.inassar.core.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidDatabaseDriver( val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = context,
            name = "database.db"
        )
}

actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { AndroidDatabaseDriver(get()) }
    }