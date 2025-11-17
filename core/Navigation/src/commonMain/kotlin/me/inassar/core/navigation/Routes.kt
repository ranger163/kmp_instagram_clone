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
/** Route for the authentication flow entry point. */
data object AuthRoute : AppRoute

@Serializable
@SerialName("Feed")
/** Route for the primary feed screen. */
data object FeedRoute : AppRoute

@Serializable
@SerialName("Profile")
/** Route for the profile screen. */
data object ProfileRoute : AppRoute

@Serializable
@SerialName("Explore")
/** Route for the explore/discover screen. */
data object ExploreRoute : AppRoute

@Serializable
@SerialName("NewPost")
/** Route for the new post compositor. */
data object NewPostRoute : AppRoute

@Serializable
@SerialName("Likes")
/** Route for the likes/notifications screen. */
data object LikesRoute : AppRoute
