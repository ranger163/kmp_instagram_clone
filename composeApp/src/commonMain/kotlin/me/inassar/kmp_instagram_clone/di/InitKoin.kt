package me.inassar.kmp_instagram_clone.di

import kotlinx.atomicfu.atomic
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

private val wasKoinStarted = atomic(false)
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (!wasKoinStarted.compareAndSet(expect = false, update = true)) {
        return // Already started
    }

    startKoin {
        appDeclaration()
        modules(allModules)
    }
}
