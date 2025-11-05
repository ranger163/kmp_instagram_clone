package me.inassar.core.ui.scafold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import me.inassar.core.navigation.AppNavHost
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.components.AppBar
import me.inassar.core.ui.components.NavBar
import me.inassar.core.ui.model.UiConfigProvider

/**
 * A reusable, shared Scaffold composable that wraps the NavHost.
 * Each screen’s Scaffold behavior is decided by its own feature module.
 */
@Composable
fun AppScaffold(
    navController: NavHostController,
    startDestination: AppRoute,
    featureEntries: List<FeatureEntry>,
    uiConfigProvider: List<UiConfigProvider>
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute: AppRoute? = backStackEntry?.let { entry ->
        featureEntries
            .asSequence()
            .mapNotNull { feature -> feature.tryCreateRoute(entry) }
            .firstOrNull()
    }

    val topConfig = currentRoute?.let { route ->
        uiConfigProvider.asSequence().mapNotNull { it.topBarConfig(route = route, navController = navController) }
            .firstOrNull()
    }

    val bottomConfig = currentRoute?.let { route ->
        uiConfigProvider.asSequence().mapNotNull { it.bottomBarConfig(route = route, navController = navController) }
            .firstOrNull()
    }

    Scaffold(
        topBar = { topConfig?.let { AppBar(it) } },
        bottomBar = { bottomConfig?.let { NavBar(it) } }
    ) { inner ->
        AppNavHost(
            modifier = Modifier.padding(inner),
            navController = navController,
            startDestination = startDestination,
            entries = featureEntries
        )
    }
}
