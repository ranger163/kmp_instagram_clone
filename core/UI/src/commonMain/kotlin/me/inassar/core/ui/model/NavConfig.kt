package me.inassar.core.ui.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import me.inassar.core.navigation.AppRoute


/**
 * A unified configuration provider for feature-level UI customization.
 * Each feature can override topBar and bottomBar appearance based on route.
 */
interface UiConfigProvider {
    /**
     * Supplies a top app bar configuration for the requested [route], or `null` to hide it.
     */
    fun topBarConfig(route: AppRoute, navController: NavHostController): TopBarConfig?

    /**
     * Supplies a bottom navigation configuration for the requested [route], or `null` to hide it.
     */
    fun bottomBarConfig(route: AppRoute, navController: NavHostController): BottomBarConfig?
}

/**
 * Immutable top app bar configuration consumed by the scaffold layer.
 *
 * @property title Text rendered at the center of the bar.
 * @property showNavigationIcon Indicates whether to show the leading nav icon.
 * @property onNavigationClick Callback for the navigation icon tap (optional).
 * @property actions Trailing action icons/actions displayed on the right edge.
 */
@Stable
data class TopBarConfig(
    val title: String = "",
    val showNavigationIcon: Boolean = false,
    val onNavigationClick: (() -> Unit)? = null,
    val actions: List<TopBarAction> = emptyList()
)

/**
 * Describes a single action icon rendered inside the top app bar.
 *
 * @property icon Icon vector displayed for the action.
 * @property contentDescription Accessibility text for screen readers.
 * @property onClick Executed when the action is tapped.
 */
data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String?,
    val onClick: () -> Unit
)

/**
 * Immutable bottom navigation bar configuration.
 *
 * @property items Ordered list of navigation destinations.
 * @property selectedIndex Currently selected item index.
 * @property onItemSelected Callback invoked when a new item is selected.
 */
@Stable
data class BottomBarConfig(
    val items: List<BottomNavItem> = emptyList(),
    val selectedIndex: Int = 0,
    val onItemSelected: (Int) -> Unit = {}
)

/**
 * Represents a single entry inside the bottom navigation bar.
 *
 * @property label Text label displayed beneath the icon.
 * @property selectedIcon Icon used when the item is active.
 * @property unselectedIcon Icon used when the item is inactive.
 */
data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
