package me.inassar.shared

class JVMPlatform : Platform {
    override val name: PlatformEnum = PlatformEnum.DESKTOP
}

actual fun getPlatform(): Platform = JVMPlatform()