package me.inassar.feature.feed.data.repository

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.FakeFeedCache
import me.inassar.feature.feed.FakeFeedRemoteApi
import me.inassar.feature.feed.FakePlatformCapabilitiesProvider
import me.inassar.feature.feed.TestDispatcherProvider
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

class FeedRepositoryImplTest {
    @Test
    fun `falls back to remote when platform lacks cache support`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto(method = "GET", status = "ok")))
        val cache = FakeFeedCache()
        val repo = FeedRepositoryImpl(
            remote = remote,
            cache = cache,
            platformCapabilities = FakePlatformCapabilitiesProvider(
                DeviceCapabilities(platform = PlatformEnum.IOS, supportsLocalCache = false)
            ),
            dispatcher = TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isSuccess)
        val feed = result.getOrThrow()
        assertFalse(feed.fromCache)
        assertEquals("GET", feed.method)
        assertEquals("ok", feed.status)
        assertEquals(1, remote.callCount)
        assertNull(cache.stored)
    }

    @Test
    fun `returns cached feed without touching network`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val cachedFeed = Feed(id = 1, method = "CACHED", status = "ok")
        val cache = FakeFeedCache(initialFeed = cachedFeed)
        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto(method = "GET", status = "remote")))
        val repo = FeedRepositoryImpl(
            remote = remote,
            cache = cache,
            platformCapabilities = FakePlatformCapabilitiesProvider(
                DeviceCapabilities(platform = PlatformEnum.ANDROID, supportsLocalCache = true)
            ),
            dispatcher = TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isSuccess)
        val feed = result.getOrThrow()
        assertTrue(feed.fromCache)
        assertEquals("CACHED", feed.method)
        assertEquals("ok", feed.status)
        assertEquals(0, remote.callCount)
    }

    @Test
    fun `persists remote response when cache empty`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val cache = FakeFeedCache()
        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto(method = "POST", status = "created")))
        val repo = FeedRepositoryImpl(
            remote = remote,
            cache = cache,
            platformCapabilities = FakePlatformCapabilitiesProvider(
                DeviceCapabilities(platform = PlatformEnum.JS, supportsLocalCache = true)
            ),
            dispatcher = TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isSuccess)
        val feed = result.getOrThrow()
        assertFalse(feed.fromCache)
        assertEquals("POST", feed.method)
        assertEquals("created", feed.status)
        assertEquals(1, remote.callCount)
        assertEquals("POST", cache.stored?.method)
        assertEquals("created", cache.stored?.status)
    }

    @Test
    fun `propagates remote failure when cache is empty`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val cache = FakeFeedCache()
        val remote = FakeFeedRemoteApi(Result.failure(IllegalStateException("boom")))
        val repo = FeedRepositoryImpl(
            remote = remote,
            cache = cache,
            platformCapabilities = FakePlatformCapabilitiesProvider(
                DeviceCapabilities(platform = PlatformEnum.WASM_JS, supportsLocalCache = true)
            ),
            dispatcher = TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isFailure)
        assertEquals(1, remote.callCount)
    }
}
