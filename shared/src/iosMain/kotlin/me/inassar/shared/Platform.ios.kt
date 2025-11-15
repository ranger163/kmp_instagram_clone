package me.inassar.shared

class IOSPlatform : Platform {
    override val name: PlatformEnum = PlatformEnum.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()