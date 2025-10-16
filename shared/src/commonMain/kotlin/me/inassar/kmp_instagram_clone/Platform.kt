package me.inassar.kmp_instagram_clone

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform