package me.inassar.feature.feed.domain.mapper

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto

class DomainFeedMapperTest {
    @Test
    fun `dto to cache params falls back to empty strings`() {
        val dto = FeedResponseDto(method = null, status = null)

        val (method, status) = dto.toCacheParams()

        assertEquals("", method)
        assertEquals("", status)
    }

    @Test
    fun `cache entity is mapped to domain with fromCache flag`() {
        val feed = Feed(id = 10, method = "POST", status = "created")

        val domain = feed.toDomain(fromCache = true)

        assertTrue(domain.fromCache)
        assertEquals("POST", domain.method)
        assertEquals("created", domain.status)
    }

    @Test
    fun `dto to domain trims nullable payload`() {
        val dto = FeedResponseDto(method = "GET", status = "ok")

        val domain = dto.toDomain()

        assertFalse(domain.fromCache)
        assertEquals("GET", domain.method)
        assertEquals("ok", domain.status)
    }
}
