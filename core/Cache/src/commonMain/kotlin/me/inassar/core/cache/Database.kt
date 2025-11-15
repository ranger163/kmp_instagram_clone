package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.module.Module

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

expect val platformCacheModule: Module