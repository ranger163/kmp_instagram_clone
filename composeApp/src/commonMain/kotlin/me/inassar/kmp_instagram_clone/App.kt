package me.inassar.kmp_instagram_clone

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.inassar.core.navigation.AuthRoute
import me.inassar.core.ui.AppTheme
import me.inassar.core.ui.common.AppConfig
import me.inassar.kmp_instagram_clone.common.getFeatures
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Root composable that wires navigation, feature registry, and shared theming.
 *
 * The optional [onNavHostReady] callback mirrors the preview/default pattern used across
 * platforms, giving hosting environments a hook to add observers (e.g., deep links) once the
 * `NavHost` has been created and the [NavController] exists.
 *
 * @param onNavHostReady Callback invoked when the navigation host is initialized.
 */
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
