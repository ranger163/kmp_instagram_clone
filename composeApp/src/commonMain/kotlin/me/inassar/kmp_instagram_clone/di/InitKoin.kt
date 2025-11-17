package me.inassar.kmp_instagram_clone.di

import kotlinx.atomicfu.atomic
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Guard that ensures the global Koin context is initialized exactly once across platforms.
 */
private val wasKoinStarted = atomic(false)

/**
 * Initializes the shared Koin graph while honoring the single-start guard.
 *
 * @param appDeclaration Optional lambda for platform-specific module registration prior to
 * calling [startKoin].
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (!wasKoinStarted.compareAndSet(expect = false, update = true)) {
        return // Already started
    }

    startKoin {
        appDeclaration()
        modules(allModules)
    }
}
