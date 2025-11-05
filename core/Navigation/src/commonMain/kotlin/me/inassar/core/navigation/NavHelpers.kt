package me.inassar.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

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
            println("Registering feature: ${entry::class.simpleName}")
            entry.register(this, navController)
        }
    }
}