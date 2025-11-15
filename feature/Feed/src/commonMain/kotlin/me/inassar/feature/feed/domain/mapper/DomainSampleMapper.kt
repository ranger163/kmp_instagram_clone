package me.inassar.feature.feed.domain.mapper

import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed

fun FeedResponseDto.toDomain() =
    DomainFeed(method = method.orEmpty(), status = status.orEmpty())