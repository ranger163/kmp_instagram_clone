package me.inassar.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

/**
 * Actual engine provider for JavaScript using the Js engine.
 *
 * Uses the browser's Fetch API (or a compatible environment) under the hood.
 */
internal actual fun platformClientEngine(): HttpClientEngine = Js.create()