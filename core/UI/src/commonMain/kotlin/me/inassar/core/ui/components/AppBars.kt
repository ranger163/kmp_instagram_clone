package me.inassar.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import me.inassar.core.ui.model.TopBarConfig


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(config: TopBarConfig) {
    TopAppBar(
        title = { Text(config.title) },
        navigationIcon = {
            if (config.showNavigationIcon && config.onNavigationClick != null) {
                IconButton(onClick = config.onNavigationClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            config.actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.contentDescription
                    )
                }
            }
        }
    )
}
