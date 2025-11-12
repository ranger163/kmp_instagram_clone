package me.inassar.feature.feed.presentation.manipulator

sealed interface FeedEvent {
    data object PingBackend : FeedEvent
}
