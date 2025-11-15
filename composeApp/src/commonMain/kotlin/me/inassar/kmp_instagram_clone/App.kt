package me.inassar.kmp_instagram_clone

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.inassar.core.navigation.AuthRoute
import me.inassar.core.ui.AppTheme
import me.inassar.core.ui.common.AppConfig
import me.inassar.kmp_instagram_clone.common.getFeatures
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val navController = rememberNavController()

    val (featureEntries, uiConfigProviders) = getFeatures()

    AppTheme(
        onNavHostReady = onNavHostReady,
        appConfig = AppConfig(
            navController = navController,
            featureEntries = featureEntries,
            startDestination = AuthRoute,
            uiConfigProvider = uiConfigProviders
        )
    )
}