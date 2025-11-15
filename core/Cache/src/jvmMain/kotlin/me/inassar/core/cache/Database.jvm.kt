package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

class JvmDatabaseDriver : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        JdbcSqliteDriver(url = "jdbc:sqlite:cache:db").also { AppDatabase.Schema.create(it) }
}

actual val platformCacheModule: Module
    get() = module {
        single <DatabaseDriverFactory>{ JvmDatabaseDriver() }
    }