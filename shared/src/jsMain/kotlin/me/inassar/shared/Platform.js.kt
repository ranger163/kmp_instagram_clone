package me.inassar.shared

class JsPlatform : Platform {
    override val name: PlatformEnum = PlatformEnum.JS
}

actual fun getPlatform(): Platform = JsPlatform()