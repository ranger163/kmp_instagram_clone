package me.inassar.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

/**
 * Actual engine provider for Android using OkHttp.
 *
 * OkHttp is the recommended engine on Android for its performance and ecosystem support.
 */
internal actual fun platformClientEngine(): HttpClientEngine = OkHttp.create()