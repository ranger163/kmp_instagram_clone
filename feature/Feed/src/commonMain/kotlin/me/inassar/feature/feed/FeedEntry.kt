package me.inassar.feature.feed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Tv
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.navigation.FeedRoute
import me.inassar.core.ui.components.BottomNavDefaults
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarAction
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider
import me.inassar.feature.feed.presentation.ui.FeedScreen

/**
 * Navigation entry + UI config provider for the feed feature.
 */
class FeedEntry : FeatureEntry, UiConfigProvider {

    /** Registers the `FeedRoute` destination and renders [FeedScreen]. */
    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<FeedRoute> { FeedScreen() }
    }

    /** Attempts to decode the back stack entry into a strongly typed [FeedRoute]. */
    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (!routeName.startsWith("Feed")) return null

        return try {
            entry.toRoute<FeedRoute>()
        } catch (e: Throwable) {
            println("Failed to decode FeedRoute: ${e.message}")
            null
        }
    }

    /** Provides a title and action icons while browsing the feed. */
    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        is FeedRoute -> TopBarConfig(
            title = "Feed",
            actions = listOf(
                TopBarAction(Icons.Default.Tv, "TV") { /* TODO */ },
                TopBarAction(Icons.AutoMirrored.Filled.Message, "Messages") { /* TODO */ }
            )
        )

        else -> null
    }

    /** Enables the shared bottom navigation when inside the feed route. */
    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = when (route) {
        is FeedRoute -> {
            BottomNavDefaults.defaultBottomBarConfig(
                currentRoute = route,
                navController = navController
            )
        }

        else -> null
    }

}
