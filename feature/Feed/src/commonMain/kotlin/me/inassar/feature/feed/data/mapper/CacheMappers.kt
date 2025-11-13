package me.inassar.feature.feed.data.mapper

import me.inassar.core.cache.feature.feed.FeedEntity
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed

fun FeedResponseDto.toEntity(): FeedEntity =
    FeedEntity(
        method = method.orEmpty(),
        status = status.orEmpty()
    )

fun FeedEntity.toDomain(): DomainFeed =
    DomainFeed(
        method = method,
        status = status
    )
