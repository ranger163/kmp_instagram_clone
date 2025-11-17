package me.inassar.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 * Shared `NavHost` wrapper that registers each feature entry into the graph.
 *
 * @param modifier Layout modifier applied to the host.
 * @param navController Controller used to navigate between destinations.
 * @param entries Registered feature entries that inject their own routes.
 * @param startDestination Initial route displayed when the host launches.
 */
@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    entries: List<FeatureEntry>,
    startDestination: AppRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        entries.forEach { entry ->
            entry.register(this, navController)
        }
    }
}
