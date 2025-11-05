package me.inassar.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    // single { HttpClient() }
}

fun assembleModules(vararg featureModules: Module): List<Module> = listOf(sharedModule) + featureModules