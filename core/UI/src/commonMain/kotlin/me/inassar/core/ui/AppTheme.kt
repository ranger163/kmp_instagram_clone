package me.inassar.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import me.inassar.core.ui.common.AppConfig
import me.inassar.core.ui.scafold.AppScaffold

/**
 * Centralizes material theming and triggers the `NavController` readiness callback.
 *
 * @param onNavHostReady Callback invoked once navigation infra is available.
 * @param appConfig Aggregated UI configuration (nav graph + scaffold config).
 */
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
