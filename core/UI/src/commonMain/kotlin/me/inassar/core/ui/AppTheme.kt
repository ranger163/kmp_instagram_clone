package me.inassar.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.model.UiConfigProvider
import me.inassar.core.ui.scafold.AppScaffold

@Composable
fun AppTheme(
    navController: NavHostController,
    onNavHostReady: suspend (NavController) -> Unit,
    startDestination: AppRoute,
    featureEntries: List<FeatureEntry>,
    uiConfigProvider: List<UiConfigProvider>
) {

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    MaterialTheme {
        AppScaffold(
            navController = navController,
            startDestination = startDestination,
            featureEntries = featureEntries,
            uiConfigProvider = uiConfigProvider
        )
    }
}