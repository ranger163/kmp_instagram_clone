package me.inassar.feature.feed.domain.model

class DomainFeed(
    val fromCache: Boolean = false,
    val method: String,
    val status: String
)