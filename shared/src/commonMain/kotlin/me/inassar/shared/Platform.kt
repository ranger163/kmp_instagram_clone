package me.inassar.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform