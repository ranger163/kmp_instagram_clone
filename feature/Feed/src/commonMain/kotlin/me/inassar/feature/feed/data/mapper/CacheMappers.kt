package me.inassar.feature.feed.data.mapper

import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed

fun FeedResponseDto.toEntity(): Feed =
    Feed(
        id = 1L,
        method = method.orEmpty(),
        status = status.orEmpty()
    )

fun Feed.toDomain(): DomainFeed =
    DomainFeed(
        method = method,
        status = status
    )
