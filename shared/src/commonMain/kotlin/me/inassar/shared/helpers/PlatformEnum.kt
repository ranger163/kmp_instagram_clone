package me.inassar.shared.helpers

/**
 * Enumerates the supported runtime targets used for feature gating and analytics.
 */
enum class PlatformEnum {
    ANDROID, IOS, DESKTOP, JS, WASM_JS, SERVER, DEFAULT
}
