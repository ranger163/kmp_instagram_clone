package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.w3c.dom.Worker

/**
 * Wasm/JS SQLDelight driver that spins up a worker-hosted SQLite runtime.
 */
class WasmJsDatabaseDriver : DatabaseDriverFactory {

    /**
     * Creates the worker-backed [SqlDriver] and initializes its schema.
     */
    override fun createDriver(): SqlDriver =
        WebWorkerDriver(worker = Worker("cache.worker.js")).also { AppDatabase.Schema.create(it) }
}

/**
 * Exposes the Wasm driver factory to consumers via DI.
 */
actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { WasmJsDatabaseDriver() }
    }
