package me.inassar.feature.auth

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.AuthRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.TopBarConfig
import me.inassar.core.ui.model.UiConfigProvider


class AuthEntry() : FeatureEntry, UiConfigProvider {

    override fun register(
        builder: NavGraphBuilder, navController: NavHostController
    ) {
        builder.composable<AuthRoute> {
            AuthScreen(
                onNavigateFeed = { feedRoute ->
                    navController.navigate(route = feedRoute)
                }
            )
        }
    }

    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (routeName != "auth") return null
        return AuthRoute
    }


    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        AuthRoute -> TopBarConfig(title = "Authentication")
        else -> null
    }

    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = null

}