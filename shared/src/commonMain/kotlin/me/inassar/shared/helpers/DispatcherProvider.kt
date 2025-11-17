package me.inassar.shared.helpers

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction over coroutine dispatchers to simplify testing/injection.
 */
interface DispatcherProvider {
    /** Dispatcher intended for blocking IO or network operations. */
    val io: CoroutineDispatcher

    /** Dispatcher for CPU-intensive or default workload tasks. */
    val default: CoroutineDispatcher

    /** Dispatcher tied to the platform's main/UI thread. */
    val main: CoroutineDispatcher
}
