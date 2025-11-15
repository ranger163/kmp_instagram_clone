package me.inassar.feature.feed.presentation.manipulator

sealed interface FeedEvent {
    data object RetrieveFeed : FeedEvent
    data object DeleteLocalFeed : FeedEvent
}
