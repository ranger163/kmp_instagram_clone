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

/**
 * Navigation entry + UI configuration provider for the authentication feature.
 */
class AuthEntry : FeatureEntry, UiConfigProvider {

    /**
     * Registers the authentication route and wires callbacks to navigate to the feed feature.
     */
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

    /**
     * Resolves the current back stack entry into [AuthRoute] when applicable.
     */
    override fun tryCreateRoute(entry: NavBackStackEntry): AppRoute? {
        val routeName = entry.destination.route ?: return null
        if (routeName != "auth") return null
        return AuthRoute
    }

    /**
     * Shows a simple title-only top bar while on the auth route.
     */
    override fun topBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): TopBarConfig? = when (route) {
        AuthRoute -> TopBarConfig(title = "Authentication")
        else -> null
    }

    /**
     * Authentication screens do not render a bottom bar.
     */
    override fun bottomBarConfig(
        route: AppRoute,
        navController: NavHostController
    ): BottomBarConfig? = null

}
