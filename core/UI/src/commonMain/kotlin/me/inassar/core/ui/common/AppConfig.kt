package me.inassar.core.ui.common

import androidx.compose.runtime.Immutable
import androidx.navigation.NavHostController
import me.inassar.core.navigation.AppRoute
import me.inassar.core.navigation.FeatureEntry
import me.inassar.core.ui.model.UiConfigProvider

@Immutable
class AppConfig(
    val navController: NavHostController,
    val startDestination: AppRoute,
    val featureEntries: List<FeatureEntry>,
    val uiConfigProvider: List<UiConfigProvider>
)