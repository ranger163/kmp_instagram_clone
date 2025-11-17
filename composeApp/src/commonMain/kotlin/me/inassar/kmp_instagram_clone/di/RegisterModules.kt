package me.inassar.kmp_instagram_clone.di

import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import me.inassar.core.cache.di.cacheModule
import me.inassar.core.network.NetworkConfig
import me.inassar.core.network.networkModule
import me.inassar.feature.auth.di.authModule
import me.inassar.feature.feed.di.feedModule
import me.inassar.shared.di.sharedModule

/**
 * Core modules shared across platforms (networking, shared utilities, cache).
 */
val appModules = networkModule(
    config = NetworkConfig(
        baseUrl = "https://dummyjson.com",
        enableLogging = true,
        logLevel = LogLevel.BODY,
        defaultHeaders = mapOf(
            HttpHeaders.ContentType to ContentType.Application.Json.toString(),
            HttpHeaders.Accept to ContentType.Application.Json.toString()
        ),
        logger = object : Logger {
            override fun log(message: String) {
                println("-> $message")
                println("------------------------")
            }
        }
    )) + sharedModule + cacheModule

/**
 * Feature-specific modules that wire screens with their data/domain dependencies.
 */
val featureModules = authModule + feedModule

/**
 * Convenience aggregation for bootstrapping Koin with a single module list.
 */
val allModules = appModules + featureModules
