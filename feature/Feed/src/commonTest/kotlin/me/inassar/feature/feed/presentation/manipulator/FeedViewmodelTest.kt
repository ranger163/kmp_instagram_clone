package me.inassar.feature.feed.presentation.manipulator

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import me.inassar.feature.feed.FakeFeedRepository
import me.inassar.feature.feed.FakePlatformCapabilitiesProvider
import me.inassar.feature.feed.TestDispatcherProvider
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum

class FeedViewmodelTest {
    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: UnconfinedTestDispatcher
    private lateinit var dispatcherProvider: TestDispatcherProvider
    private lateinit var capabilities: DeviceCapabilities
    private lateinit var capabilitiesProvider: FakePlatformCapabilitiesProvider

    @BeforeTest
    fun setup() {
        scheduler = TestCoroutineScheduler()
        dispatcher = UnconfinedTestDispatcher(scheduler)
        dispatcherProvider = TestDispatcherProvider(dispatcher)
        capabilities = DeviceCapabilities(platform = PlatformEnum.ANDROID, supportsLocalCache = true)
        capabilitiesProvider = FakePlatformCapabilitiesProvider(capabilities)
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state mirrors platform capabilities`() {
        val repo = FakeFeedRepository(Result.success(DomainFeed(method = "GET", status = "ok")))

        val viewmodel = FeedViewmodel(repo, capabilitiesProvider, dispatcherProvider)

        val state = viewmodel.state.value
        assertEquals(capabilities, state.capabilities)
        assertFalse(state.isLoading)
        assertNull(state.data)
        assertNull(state.error)
    }

    @Test
    fun `successful retrieve updates message and clears error`() {
        val repo = FakeFeedRepository(Result.success(DomainFeed(method = "GET", status = "ok")))
        val viewmodel = FeedViewmodel(repo, capabilitiesProvider, dispatcherProvider)

        viewmodel.onAction(FeedEvent.RetrieveFeed)

        val state = viewmodel.state.value
        assertFalse(state.isLoading)
        assertEquals("Cache empty, fetching from remote\nRemote data: ok (GET)", state.data)
        assertNull(state.error)
        assertEquals(1, repo.retrieveCallCount)
    }

    @Test
    fun `failure during retrieve exposes error message`() {
        val repo = FakeFeedRepository(Result.failure(IllegalStateException("boom")))
        val viewmodel = FeedViewmodel(repo, capabilitiesProvider, dispatcherProvider)

        viewmodel.onAction(FeedEvent.RetrieveFeed)

        val state = viewmodel.state.value
        assertFalse(state.isLoading)
        assertNull(state.data)
        assertEquals("Error: boom", state.error)
    }

    @Test
    fun `delete local feed clears ui state`() {
        val repo = FakeFeedRepository(Result.success(DomainFeed(method = "GET", status = "ok")))
        val viewmodel = FeedViewmodel(repo, capabilitiesProvider, dispatcherProvider)
        viewmodel.onAction(FeedEvent.RetrieveFeed)
        assertNull(viewmodel.state.value.error)

        viewmodel.onAction(FeedEvent.DeleteLocalFeed)

        val state = viewmodel.state.value
        assertNull(state.data)
        assertNull(state.error)
        assertFalse(state.isLoading)
        assertEquals(1, repo.deleteCallCount)
    }
}
