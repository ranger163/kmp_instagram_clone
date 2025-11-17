package me.inassar.kmp_instagram_clone

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.inassar.kmp_instagram_clone.di.initKoin

/**
 * Desktop entry point that starts Koin and launches the Compose window host.
 */
fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "kmp_instagram_clone",
        ) {
            App()
        }
    }
}
