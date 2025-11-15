package me.inassar.shared

interface Platform {
    val name: PlatformEnum
}

expect fun getPlatform(): Platform