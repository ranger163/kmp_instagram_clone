package me.inassar.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*

/**
 * Actual engine provider for JVM using the CIO engine.
 *
 * CIO is Ktor's coroutine-based I/O engine optimized for JVM server and desktop use.
 */
internal actual fun platformClientEngine(): HttpClientEngine = CIO.create()