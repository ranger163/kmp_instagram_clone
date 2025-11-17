package me.inassar.core.ui.scafold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import me.inassar.core.navigation.AppNavHost
import me.inassar.core.navigation.AppRoute
import me.inassar.core.ui.common.AppConfig
import me.inassar.core.ui.components.AppBar
import me.inassar.core.ui.components.NavBar

/**
 * Shared scaffold wrapper that hoists navigation, top app bar, and bottom nav behavior.
 *
 * @param appConfig Provides nav controller, start destination, feature entries, and UI config providers.
 */
@Composable
fun AppScaffold(
    appConfig: AppConfig
) {

    val backStackEntry by appConfig.navController.currentBackStackEntryAsState()

    val currentRoute: AppRoute? = backStackEntry?.let { entry ->
        appConfig.featureEntries
            .asSequence()
            .mapNotNull { feature -> feature.tryCreateRoute(entry) }
            .firstOrNull()
    }

    val topConfig = currentRoute?.let { route ->
        appConfig.uiConfigProvider.asSequence().mapNotNull { it.topBarConfig(route = route, navController = appConfig.navController) }
            .firstOrNull()
    }

    val bottomConfig = currentRoute?.let { route ->
        appConfig.uiConfigProvider.asSequence().mapNotNull { it.bottomBarConfig(route = route, navController = appConfig.navController) }
            .firstOrNull()
    }

    Scaffold(
        topBar = { topConfig?.let { AppBar(it) } },
        bottomBar = { bottomConfig?.let { NavBar(it) } }
    ) { inner ->
        AppNavHost(
            modifier = Modifier.padding(inner),
            navController = appConfig.navController,
            startDestination = appConfig.startDestination,
            entries = appConfig.featureEntries
        )
    }
}
