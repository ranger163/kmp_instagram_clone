package me.inassar.feature.feed.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.FakeFeedCache
import me.inassar.feature.feed.FakeFeedRemoteApi
import me.inassar.feature.feed.FakePlatformCapabilitiesProvider
import me.inassar.feature.feed.TestDispatcherProvider
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum
import kotlin.test.*

class FeedRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `falls back to remote when cache unsupported`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto("GET", "ok")))
        val cache = FakeFeedCache()
        val repo = FeedRepositoryImpl(
            remote = remote,
            cache = cache,
            platformCapabilities = FakePlatformCapabilitiesProvider(
                DeviceCapabilities(PlatformEnum.WASM_JS, supportsLocalCache = false)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `returns cached feed when available`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val cached = Feed(id = 1, method = "CACHED", status = "ok")
        val cache = FakeFeedCache(cached)
        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto("GET", "remote")))
        val repo = FeedRepositoryImpl(
            remote, cache,
            FakePlatformCapabilitiesProvider(
                DeviceCapabilities(PlatformEnum.ANDROID, supportsLocalCache = true)
            ),
            TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isSuccess)
        val feed = result.getOrThrow()
        assertTrue(feed.fromCache)
        assertEquals("CACHED", feed.method)
        assertEquals("ok", feed.status)
        assertEquals(0, remote.callCount)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `saves remote response when cache empty`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val cache = FakeFeedCache()
        val remote = FakeFeedRemoteApi(Result.success(FeedResponseDto("POST", "created")))
        val repo = FeedRepositoryImpl(
            remote, cache,
            FakePlatformCapabilitiesProvider(
                DeviceCapabilities(PlatformEnum.DESKTOP, supportsLocalCache = true)
            ),
            TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()
        assertTrue(result.isSuccess)

        val feed = result.getOrThrow()
        assertFalse(feed.fromCache)
        assertEquals("POST", feed.method)
        assertEquals("created", feed.status)
        assertEquals("POST", cache.stored?.method)
        assertEquals("created", cache.stored?.status)
        assertEquals(1, remote.callCount)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `propagates remote failure when cache empty`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val cache = FakeFeedCache()
        val remote = FakeFeedRemoteApi(Result.failure(IllegalStateException("boom")))
        val repo = FeedRepositoryImpl(
            remote, cache,
            FakePlatformCapabilitiesProvider(
                DeviceCapabilities(PlatformEnum.IOS, supportsLocalCache = true)
            ),
            TestDispatcherProvider(dispatcher)
        )

        val result = repo.retrieveFeed()

        assertTrue(result.isFailure)
        assertEquals(1, remote.callCount)
    }
}
