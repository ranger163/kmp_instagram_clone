package me.inassar.core.network

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

/**
 * Actual engine provider for iOS using Darwin.
 *
 * The Darwin engine integrates with NSURLSession and is recommended for Apple platforms.
 */
internal actual fun platformClientEngine(): HttpClientEngine = Darwin.create()