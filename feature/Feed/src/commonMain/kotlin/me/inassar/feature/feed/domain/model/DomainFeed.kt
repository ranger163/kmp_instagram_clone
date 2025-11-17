package me.inassar.feature.feed.domain.model

/**
 * Domain representation of the feed payload enriched with metadata.
 *
 * @property fromCache Indicates whether the data originated from the cache.
 * @property method HTTP method echoed by the backend.
 * @property status Status string returned by the backend.
 */
class DomainFeed(
    val fromCache: Boolean = false,
    val method: String,
    val status: String
)
