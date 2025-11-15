package me.inassar.shared

fun isJsPlatform() = getPlatform().name == PlatformEnum.JS || getPlatform().name == PlatformEnum.WASM_JS