package me.inassar.core.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/** Contract every feature implements to register its destinations. */
interface FeatureEntry {
    /**
     * Register this feature's composable destinations into the shared nav graph.
     *
     * @param builder Graph builder provided by the host nav setup.
     * @param navController Shared nav controller exposed to destinations.
     */
    fun register(builder: NavGraphBuilder, navController: NavHostController)


    /**
     * Try to decode this back stack entry into this feature's typed route.
     * Return null if it doesn't belong to this feature.
     *
     * @param entry Back stack entry to inspect.
     */
    fun tryCreateRoute(entry: NavBackStackEntry): AppRoute?
}
