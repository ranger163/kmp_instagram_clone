package me.inassar.feature.likes

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.navigation.LikesRoute
import me.inassar.core.ui.components.BottomNavDefaults
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider

class LikesEntry : FeatureEntry, UiConfigProvider {

    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<LikesRoute> { LikesScreen() }
    }

    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (!routeName.startsWith("Likes")) return null

        return try {
            entry.toRoute<LikesRoute>()
        } catch (e: Throwable) {
            println("Failed to decode LikesRoute: ${e.message}")
            null
        }
    }

    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        is LikesRoute -> TopBarConfig(
            title = "Likes",
            showNavigationIcon = false
        )

        else -> null
    }

    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = when (route) {
        is LikesRoute -> {
            BottomNavDefaults.defaultBottomBarConfig(
                currentRoute = route,
                navController = navController
            )
        }

        else -> null
    }

}