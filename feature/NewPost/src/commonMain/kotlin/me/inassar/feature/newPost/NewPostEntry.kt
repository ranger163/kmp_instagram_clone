package me.inassar.feature.newPost

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.navigation.NewPostRoute
import me.inassar.core.ui.components.BottomNavDefaults
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider

class NewPostEntry : FeatureEntry, UiConfigProvider {

    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<NewPostRoute> { NewPostScreen() }
    }

    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (!routeName.startsWith("NewPost")) return null

        return try {
            entry.toRoute<NewPostRoute>()
        } catch (e: Throwable) {
            println("Failed to decode NewPostRoute: ${e.message}")
            null
        }
    }

    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        is NewPostRoute -> TopBarConfig(
            title = "NewPost",
            showNavigationIcon = false
        )

        else -> null
    }

    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = when (route) {
        is NewPostRoute -> {
            BottomNavDefaults.defaultBottomBarConfig(
                currentRoute = route,
                navController = navController
            )
        }

        else -> null
    }

}