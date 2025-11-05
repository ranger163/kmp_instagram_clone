package me.inassar.kmp_instagram_clone

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.inassar.core.navigation.AuthRoute
import me.inassar.core.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val navController = rememberNavController()

    val (featureEntries, uiConfigProviders) = getFeatures()


    AppTheme(
        navController = navController,
        onNavHostReady = onNavHostReady,
        featureEntries = featureEntries,
        startDestination = AuthRoute,
        uiConfigProvider = uiConfigProviders
    )
}