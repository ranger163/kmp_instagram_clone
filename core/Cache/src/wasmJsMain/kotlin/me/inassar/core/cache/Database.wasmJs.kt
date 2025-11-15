package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import me.inassar.core.cache.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.w3c.dom.Worker

class WasmJsDatabaseDriver : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        WebWorkerDriver(worker = Worker("cache.worker.js")).also { AppDatabase.Schema.create(it) }
}

actual val platformCacheModule: Module
    get() = module {
        single<DatabaseDriverFactory> { WasmJsDatabaseDriver() }
    }