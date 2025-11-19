package me.inassar.feature.feed

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.inassar.core.cache.db.Feed
import me.inassar.feature.feed.data.cache.FeedCache
import me.inassar.feature.feed.data.remote.FeedRemoteApi
import me.inassar.feature.feed.data.remote.dto.feed.response.FeedResponseDto
import me.inassar.feature.feed.domain.model.DomainFeed
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.shared.helpers.DeviceCapabilities
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider

internal class TestDispatcherProvider(private val dispatcher: CoroutineDispatcher) : DispatcherProvider {
    override val io: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
}

internal class FakePlatformCapabilitiesProvider(
    private val capabilities: DeviceCapabilities,
) : PlatformCapabilitiesProvider {
    override fun getCapabilities(): DeviceCapabilities = capabilities
}

internal class FakeFeedCache(initialFeed: Feed? = null) : FeedCache {
    var stored: Feed? = initialFeed
        private set
    var insertCalls: Int = 0
        private set
    var deleteCalls: Int = 0
        private set
    private var nextId: Long = (initialFeed?.id ?: 0L) + 1L

    override suspend fun insertFeed(method: String, status: String) {
        insertCalls++
        stored = Feed(id = nextId++, method = method, status = status)
    }

    override fun getFeed(): Flow<Feed?> = flowOf(stored)

    override suspend fun deleteFeed() {
        deleteCalls++
        stored = null
    }
}

internal class FakeFeedRemoteApi(
    var nextResult: Result<FeedResponseDto>,
) : FeedRemoteApi {
    var callCount: Int = 0
        private set

    override suspend fun pingBackend(): Result<FeedResponseDto> {
        callCount++
        return nextResult
    }
}

internal class FakeFeedRepository(
    var retrieveResult: Result<DomainFeed>,
) : FeedRepository {
    var retrieveCallCount: Int = 0
        private set
    var deleteCallCount: Int = 0
        private set

    override suspend fun retrieveFeed(): Result<DomainFeed> {
        retrieveCallCount++
        return retrieveResult
    }

    override suspend fun deleteLocalFeed() {
        deleteCallCount++
    }
}
