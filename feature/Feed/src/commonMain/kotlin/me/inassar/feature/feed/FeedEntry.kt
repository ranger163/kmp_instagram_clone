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

class FeedEntry : FeatureEntry, UiConfigProvider {

    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<FeedRoute> { FeedScreen() }
    }

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
