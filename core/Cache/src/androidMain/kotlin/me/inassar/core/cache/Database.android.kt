package me.inassar.core.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-specific SQLDelight driver that relies on `AndroidSqliteDriver`.
 *
 * @property context Application context used to open/create the SQLite database file.
 */
class AndroidDatabaseDriver(val context: Context) : DatabaseDriverFactory {

    /**
     * Creates the [SqlDriver] backed by Android's SQLite implementation.
     */
    override fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = context,
            name = "database.db"
        )
}

/**
 * Binds the Android driver factory to the DI graph.
 */
actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { AndroidDatabaseDriver(get()) }
    }
