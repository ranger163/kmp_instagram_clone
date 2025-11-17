package me.inassar.feature.feed.domain.mapper

import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed

fun FeedResponseDto.toCacheParams(): Pair<String, String> =
    method.orEmpty() to status.orEmpty()

fun Feed.toDomain(fromCache: Boolean): DomainFeed =
    DomainFeed(
        fromCache = fromCache,
        method = method,
        status = status
    )

fun FeedResponseDto.toDomain() =
    DomainFeed(method = method.orEmpty(), status = status.orEmpty())