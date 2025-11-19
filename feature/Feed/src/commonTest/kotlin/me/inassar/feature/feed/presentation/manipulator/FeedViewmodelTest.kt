package me.inassar.feature.feed.presentation.manipulator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import me.inassar.feature.feed.FakeFeedRepository
import me.inassar.feature.feed.FakePlatformCapabilitiesProvider
import me.inassar.feature.feed.TestDispatcherProvider
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.PlatformEnum
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewmodelTest {

    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: TestDispatcher
    private lateinit var dispatcherProvider: TestDispatcherProvider
    private lateinit var capabilities: DeviceCapabilities
    private lateinit var capabilitiesProvider: FakePlatformCapabilitiesProvider

    @BeforeTest
    fun setup() {
        // one scheduler/dispatcher shared across the test scope + ViewModel coroutines
        scheduler = TestCoroutineScheduler()
        dispatcher = UnconfinedTestDispatcher(scheduler)
        dispatcherProvider = TestDispatcherProvider(dispatcher)

        capabilities = DeviceCapabilities(
            platform = PlatformEnum.ANDROID,
            supportsLocalCache = true
        )
        capabilitiesProvider = FakePlatformCapabilitiesProvider(capabilities)

        // make viewModelScope/Main use the test dispatcher on JVM/native/JS
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        result: Result<DomainFeed>
    ): Pair<FeedViewmodel, FakeFeedRepository> {
        val repo = FakeFeedRepository(result)
        val viewModel = FeedViewmodel(
            repo = repo,
            platformCapabilities = capabilitiesProvider,
            dispatcher = dispatcherProvider
        )
        return viewModel to repo
    }

    @Test
    fun `initial state mirrors platform capabilities and is idle`() = runTest(scheduler) {
        val (viewModel, _) = createViewModel(
            Result.success(DomainFeed(method = "GET", status = "ok"))
        )

        // No async work expected at init, but advance just in case
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(capabilities, state.capabilities)
        assertFalse(state.isLoading)
        assertNull(state.data)
        assertNull(state.error)
    }

    @Test
    fun `successful retrieve updates message and clears error`() = runTest(scheduler) {
        val (viewModel, repo) = createViewModel(
            Result.success(DomainFeed(method = "GET", status = "ok"))
        )

        viewModel.onAction(FeedEvent.RetrieveFeed)
        advanceUntilIdle()

        val state = viewModel.state.value

        // UI state
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertNotNull(state.data)

        // We don’t assert the exact sentence, only that it reflects the domain data
        assertTrue(state.data.contains("GET"), "Expected method in UI message")
        assertTrue(state.data.contains("ok"), "Expected status in UI message")

        // Repository interaction
        assertEquals(1, repo.retrieveCallCount)
        assertEquals(0, repo.deleteCallCount)
    }

    @Test
    fun `failure during retrieve exposes error message`() = runTest(scheduler) {
        val (viewModel, repo) = createViewModel(
            Result.failure(IllegalStateException("boom"))
        )

        viewModel.onAction(FeedEvent.RetrieveFeed)
        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertNull(state.data)
        assertNotNull(state.error)
        assertTrue(
            state.error.contains("boom"),
            "Expected propagated error message to contain cause"
        )

        assertEquals(1, repo.retrieveCallCount)
        assertEquals(0, repo.deleteCallCount)
    }

    @Test
    fun `delete local feed clears ui state`() = runTest(scheduler) {
        val (viewModel, repo) = createViewModel(
            Result.success(DomainFeed(method = "GET", status = "ok"))
        )

        // First retrieve something to populate state
        viewModel.onAction(FeedEvent.RetrieveFeed)
        advanceUntilIdle()
        assertNull(viewModel.state.value.error)

        // Then delete
        viewModel.onAction(FeedEvent.DeleteLocalFeed)
        advanceUntilIdle()

        val state = viewModel.state.value

        assertNull(state.data)
        assertNull(state.error)
        assertFalse(state.isLoading)
        assertEquals(1, repo.deleteCallCount)
    }
}
