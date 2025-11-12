package me.inassar.feature.feed.di

import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.data.remote.source.FeedRemoteApiImpl
import me.inassar.feature.feed.data.repository.FeedRepositoryImpl
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.feature.feed.presentation.manipulator.FeedViewmodel
import org.koin.dsl.module


val feedModule = module {
    single<FeedRemoteApi> { FeedRemoteApiImpl(get()) }
    single<FeedRepository> { FeedRepositoryImpl(get()) }
    factory { FeedViewmodel(get()) }
}
