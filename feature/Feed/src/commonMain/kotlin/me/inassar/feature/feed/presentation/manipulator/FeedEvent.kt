package me.inassar.feature.feed.presentation.manipulator

/**
 * UI-triggered events handled by the [FeedViewmodel].
 */
sealed interface FeedEvent {
    /** Request to fetch the feed from the repository. */
    data object RetrieveFeed : FeedEvent

    /** Request to clear the cached feed entry. */
    data object DeleteLocalFeed : FeedEvent
}
