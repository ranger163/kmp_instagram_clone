package me.inassar.core.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Marker for all typed routes in the app.
 * Features define their own route classes implementing this.
 */
@Serializable
sealed interface AppRoute


@Serializable
@SerialName("auth")
data object AuthRoute : AppRoute

@Serializable
@SerialName("Feed")
data object FeedRoute : AppRoute

@Serializable
@SerialName("Profile")
data object ProfileRoute : AppRoute

@Serializable
@SerialName("Explore")
data object ExploreRoute : AppRoute

@Serializable
@SerialName("NewPost")
data object NewPostRoute : AppRoute

@Serializable
@SerialName("Likes")
data object LikesRoute : AppRoute
