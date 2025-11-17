package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.w3c.dom.Worker

/**
 * JavaScript SQLDelight driver leveraging a WebWorker to host the SQLite runtime.
 */
class JsDatabaseDriver : DatabaseDriverFactory {

    /**
     * Creates a [WebWorkerDriver] and initializes the schema inside the worker context.
     */
    override fun createDriver(): SqlDriver {
        return WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        ).also { AppDatabase.Schema.create(it) }
    }
}

/**
 * Registers the JS driver factory so cache consumers can depend on [DatabaseDriverFactory].
 */
actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { JsDatabaseDriver() }
    }
