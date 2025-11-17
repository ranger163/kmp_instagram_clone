package me.inassar.feature.explore

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.ExploreRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.components.BottomNavDefaults
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider

/** Navigation entry for the explore feature. */
class ExploreEntry : FeatureEntry, UiConfigProvider {

    /** Registers the explore destination. */
    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<ExploreRoute> { ExploreScreen() }
    }

    /** Attempts to decode the explore route from the back stack entry. */
    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (!routeName.startsWith("Explore")) return null

        return try {
            entry.toRoute<ExploreRoute>()
        } catch (e: Throwable) {
            println("Failed to decode ExploreRoute: ${e.message}")
            null
        }
    }

    /** Provides an `Explore` title-only top bar. */
    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        is ExploreRoute -> TopBarConfig(
            title = "Explore",
            showNavigationIcon = false
        )

        else -> null
    }

    /** Shows the shared bottom bar on explore screens. */
    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = when (route) {
        is ExploreRoute -> {
            BottomNavDefaults.defaultBottomBarConfig(
                currentRoute = route,
                navController = navController
            )
        }

        else -> null
    }

}
