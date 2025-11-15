package me.inassar.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

/**
 * Actual engine provider for Wasm/JS using the Js engine.
 *
 * Relies on the browser (or host) Fetch implementation available to wasm targets.
 */
internal actual fun platformClientEngine(): HttpClientEngine = Js.create()