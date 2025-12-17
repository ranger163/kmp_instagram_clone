package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

/**
 * Stores the absolute path to the SQLite DB used on JVM/desktop targets.
 */
private val dbPath = File(
    System.getProperty("user.home"),
    ".kmp-instagram/cache/feed.db"
).apply { parentFile?.mkdirs() }.absolutePath

/**
 * JVM-specific SQLDelight driver that persists data to a file system path.
 */
class JvmDatabaseDriver : DatabaseDriverFactory {

    /**
     * Creates a JDBC-backed SQLite driver and initializes the schema if needed.
     */
    override fun createDriver(): SqlDriver {
        val dbFile = File(dbPath)
        val isNewDb = !dbFile.exists()

        return JdbcSqliteDriver(url = "jdbc:sqlite:$dbPath").also { driver ->
            // Only run schema creation when the database file is first created.
            if (isNewDb) {
                AppDatabase.Schema.create(driver)
            }
        }
    }
}

/**
 * Binds the JVM driver factory for dependency injection.
 */
actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { JvmDatabaseDriver() }
    }
