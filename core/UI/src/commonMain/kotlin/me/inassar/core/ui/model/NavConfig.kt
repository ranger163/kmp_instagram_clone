package me.inassar.core.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import me.inassar.core.navigation.AppRoute


/**
 * A unified configuration provider for feature-level UI customization.
 * Each feature can override topBar and bottomBar appearance based on route.
 */
interface UiConfigProvider {
    fun topBarConfig(route: AppRoute, navController: NavHostController): TopBarConfig?
    fun bottomBarConfig(route: AppRoute, navController: NavHostController): BottomBarConfig?
}

data class TopBarConfig(
    val title: String = "",
    val showNavigationIcon: Boolean = false,
    val onNavigationClick: (() -> Unit)? = null,
    val actions: List<TopBarAction> = emptyList()
)

data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String?,
    val onClick: () -> Unit
)

data class BottomBarConfig(
    val items: List<BottomNavItem> = emptyList(),
    val selectedIndex: Int = 0,
    val onItemSelected: (Int) -> Unit = {}
)

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
