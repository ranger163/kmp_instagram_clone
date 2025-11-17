package me.inassar.kmp_instagram_clone

import androidx.compose.ui.window.ComposeUIViewController

/**
 * iOS bridge that embeds the shared Compose [App] inside a `UIViewController`.
 */
fun MainViewController() = ComposeUIViewController {
    App()
}
