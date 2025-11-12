package me.inassar.feature.feed.presentation.mapper

import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.presentation.data.UiFeed

fun DomainFeed.toUi() = UiFeed("Backend status: $status ($method)")