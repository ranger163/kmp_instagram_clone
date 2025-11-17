package me.inassar.kmp_instagram_clone

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import me.inassar.kmp_instagram_clone.di.initKoin

/**
 * Web entry point that initializes DI and binds navigation to the browser history API.
 */
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalBrowserHistoryApi
fun main() {
    initKoin()

    ComposeViewport {
        App(onNavHostReady = { navController -> navController.bindToBrowserNavigation() })
    }
}
