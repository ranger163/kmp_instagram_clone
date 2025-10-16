package me.inassar.kmp_instagram_clone

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp_instagram_clone",
    ) {
        App()
    }
}