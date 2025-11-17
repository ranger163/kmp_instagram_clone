package me.inassar.feature.feed.presentation.mapper

import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.presentation.data.UiFeed

/** Converts the domain model into a display-friendly [UiFeed]. */
fun DomainFeed.toUi() = UiFeed("$status ($method)")
