package me.inassar.kmp_instagram_clone.common

import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.model.UiConfigProvider
import me.inassar.feature.auth.AuthEntry
import me.inassar.feature.explore.ExploreEntry
import me.inassar.feature.feed.FeedEntry
import me.inassar.feature.likes.LikesEntry
import me.inassar.feature.newPost.NewPostEntry
import me.inassar.feature.profile.ProfileEntry

fun getFeatures(): Pair<List<FeatureEntry>, List<UiConfigProvider>> {
    val features: List<FeatureEntry> = listOf(
        AuthEntry(),
        FeedEntry(),
        ExploreEntry(),
        NewPostEntry(),
        LikesEntry(),
        ProfileEntry()
    )

    val uiConfigProvider: List<UiConfigProvider> = features.filterIsInstance<UiConfigProvider>()

    return Pair(features, uiConfigProvider)
}