package me.inassar.kmp_instagram_clone

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import me.inassar.kmp_instagram_clone.di.initKoin

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalBrowserHistoryApi
fun main() {
    initKoin()

    ComposeViewport {
        App(onNavHostReady = { navController -> navController.bindToBrowserNavigation() })
    }
}
