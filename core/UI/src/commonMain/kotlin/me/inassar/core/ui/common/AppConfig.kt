package me.inassar.core.ui.common

import androidx.compose.runtime.Immutable
import androidx.navigation.NavHostController
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.model.UiConfigProvider

/**
 * Aggregates all UI-related dependencies required to build the scaffold/nav host.
 *
 * @property navController Primary `NavHostController` shared across features.
 * @property startDestination Graph entry route used when launching the app.
 * @property featureEntries Registered feature entry points used to build the nav graph.
 * @property uiConfigProvider Optional providers that customize scaffold UI per destination.
 */
@Immutable
class AppConfig(
    val navController: NavHostController,
    val startDestination: AppRoute,
    val featureEntries: List<FeatureEntry>,
    val uiConfigProvider: List<UiConfigProvider>
)
