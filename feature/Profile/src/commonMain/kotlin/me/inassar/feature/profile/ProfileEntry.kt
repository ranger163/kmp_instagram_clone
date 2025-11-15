package me.inassar.feature.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.AuthRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.navigation.ProfileRoute
import me.inassar.core.ui.components.BottomNavDefaults
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarAction
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider

class ProfileEntry : FeatureEntry, UiConfigProvider {

    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable<ProfileRoute> { ProfileScreen() }
    }

    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (!routeName.startsWith("Profile")) return null

        return try {
            entry.toRoute<ProfileRoute>()
        } catch (e: Throwable) {
            println("Failed to decode ProfileRoute: ${e.message}")
            null
        }
    }

    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        is ProfileRoute -> TopBarConfig(
            title = "Profile",
            showNavigationIcon = false,
            actions = listOf(
                TopBarAction(Icons.AutoMirrored.Filled.Logout, "Logout") {
                    navController.navigate(AuthRoute) {
                        popUpTo(AuthRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        )

        else -> null
    }

    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = when (route) {
        is ProfileRoute -> {
            BottomNavDefaults.defaultBottomBarConfig(
                currentRoute = route,
                navController = navController
            )
        }

        else -> null
    }

}