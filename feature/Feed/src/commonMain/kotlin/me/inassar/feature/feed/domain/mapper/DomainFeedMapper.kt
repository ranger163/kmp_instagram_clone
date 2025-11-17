package me.inassar.feature.feed.domain.mapper

import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed

/** Converts the remote DTO into cache insert parameters. */
fun FeedResponseDto.toCacheParams(): Pair<String, String> =
    method.orEmpty() to status.orEmpty()

/** Maps a cached feed row into the domain model. */
fun Feed.toDomain(fromCache: Boolean): DomainFeed =
    DomainFeed(
        fromCache = fromCache,
        method = method,
        status = status
    )

/** Converts the remote DTO into the domain model without cache metadata. */
fun FeedResponseDto.toDomain() =
    DomainFeed(method = method.orEmpty(), status = status.orEmpty())
