package me.inassar.shared

class AndroidPlatform : Platform {
    override val name: PlatformEnum = PlatformEnum.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()