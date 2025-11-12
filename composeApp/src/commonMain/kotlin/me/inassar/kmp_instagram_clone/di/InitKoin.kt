package me.inassar.kmp_instagram_clone.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(allModules)
    }
}
