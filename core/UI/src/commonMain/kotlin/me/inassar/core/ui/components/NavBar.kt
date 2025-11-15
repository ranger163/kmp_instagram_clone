package me.inassar.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import me.inassar.core.navigation.*
import me.inassar.core.ui.model.BottomBarConfig
import me.inassar.core.ui.model.BottomNavItem

/**
 * Type-safe destinations for the bottom navigation bar.
 * Each represents a tab and knows which [AppRoute] it navigates to.
 */
sealed class BottomNavDestination(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: AppRoute
) {
    object Feed : BottomNavDestination(
        label = "Feed",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = FeedRoute
    )

    object Explore : BottomNavDestination(
        label = "Explore",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        route = ExploreRoute
    )

    object NewPost : BottomNavDestination(
        label = "NewPost",
        selectedIcon = Icons.Filled.PostAdd,
        unselectedIcon = Icons.Outlined.PostAdd,
        route = NewPostRoute
    )

    object Likes : BottomNavDestination(
        label = "Likes",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        route = LikesRoute
    )

    object Profile : BottomNavDestination(
        label = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        route = ProfileRoute
    )

    companion object {
        val all = listOf(Feed, Explore, NewPost, Likes, Profile)
    }
}


/**
 * Centralized bottom navigation configuration shared across features.
 */
object BottomNavDefaults {

    /**
     * Build the default BottomBarConfig with all shared tabs.
     */
    fun defaultBottomBarConfig(
        currentRoute: AppRoute,
        navController: NavHostController,
    ): BottomBarConfig {
        val destinations = BottomNavDestination.all

        val currentIndex = destinations.indexOfFirst { dest ->
            dest.route::class == currentRoute::class
        }.coerceAtLeast(0)

        return BottomBarConfig(
            items = destinations.map { dest ->
                BottomNavItem(
                    label = dest.label,
                    selectedIcon = dest.selectedIcon,
                    unselectedIcon = dest.unselectedIcon
                )
            },
            selectedIndex = currentIndex,
            onItemSelected = { index ->
                val targetRoute = destinations[index].route
                if (currentRoute::class != targetRoute::class) {
                    navController.navigate(targetRoute)
                }
            }
        )
    }
}


@Composable
fun NavBar(config: BottomBarConfig) {
    NavigationBar {
        config.items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == config.selectedIndex,
                onClick = { config.onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (index == config.selectedIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) })
        }
    }
}
