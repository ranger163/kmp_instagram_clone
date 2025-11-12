package me.inassar.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import me.inassar.core.ui.common.AppConfig
import me.inassar.core.ui.scafold.AppScaffold

@Composable
fun AppTheme(
    onNavHostReady: suspend (NavController) -> Unit,
    appConfig: AppConfig
) {

    LaunchedEffect(appConfig.navController) {
        onNavHostReady(appConfig.navController)
    }

    MaterialTheme {
        AppScaffold(
            appConfig = appConfig
        )
    }
}