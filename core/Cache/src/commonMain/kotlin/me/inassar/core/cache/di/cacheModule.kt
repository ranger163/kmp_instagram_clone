package me.inassar.core.cache.di

import me.inassar.core.cache.AppDatabase
import me.inassar.core.cache.feature.feed.FeedDao
import me.inassar.core.cache.getRoomDatabase
import me.inassar.core.cache.platformCacheModule
import org.koin.dsl.module

val cacheModule = module {
    includes(platformCacheModule)
    single<AppDatabase> { getRoomDatabase(builder = get()) }

    single<FeedDao> { get<AppDatabase>().getFeedDao() }
}