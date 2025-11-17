package me.inassar.core.cache

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.module.Module

/**
 * Factory abstraction that hides platform-specific SQLDelight driver creation.
 */
interface DatabaseDriverFactory {
    /**
     * Builds the [SqlDriver] instance appropriate for the current platform.
     */
    fun createDriver(): SqlDriver
}

/**
 * Platform-specific Koin module that binds the SQLDelight driver and cache implementations.
 */
expect val platformCacheModule: Module
