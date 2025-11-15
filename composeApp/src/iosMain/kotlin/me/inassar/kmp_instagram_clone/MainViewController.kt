package me.inassar.kmp_instagram_clone

import androidx.compose.ui.window.ComposeUIViewController
import me.inassar.kmp_instagram_clone.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
