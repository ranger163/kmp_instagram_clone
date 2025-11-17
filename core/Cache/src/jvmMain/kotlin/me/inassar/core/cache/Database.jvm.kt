package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

private val dbPath = File(
    System.getProperty("user.home"),
    ".kmp-instagram/cache/feed.db"
).apply { parentFile?.mkdirs() }.absolutePath

class JvmDatabaseDriver : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        JdbcSqliteDriver(url = "jdbc:sqlite:$dbPath").also { AppDatabase.Schema.create(it) }
}

actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { JvmDatabaseDriver() }
    }