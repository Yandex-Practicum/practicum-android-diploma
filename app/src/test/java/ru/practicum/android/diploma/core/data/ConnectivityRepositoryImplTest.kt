package ru.practicum.android.diploma.core.data

import android.net.*
import app.cash.turbine.test
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.practicum.android.diploma.core.data.repository.ConnectivityRepositoryImpl

@ExperimentalCoroutinesApi
class ConnectivityRepositoryImplTest {
    private lateinit var mockConnectivityManager: ConnectivityManager
    private lateinit var mockNetwork: Network
    private lateinit var  networkRequestMock: NetworkRequest
    private lateinit var mockCapabilities: NetworkCapabilities

    private lateinit var repository: ConnectivityRepositoryImpl
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before fun setUp() {
        mockkStatic(ConnectivityManager::class)
        mockConnectivityManager = mockk(relaxed = true)
        mockNetwork = mockk(relaxed = true)
        networkRequestMock = mockk(relaxed = true)
        mockCapabilities = mockk(relaxed = true)

        repository = ConnectivityRepositoryImpl(mockConnectivityManager)

        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    @After fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test fun `isConnected returns true when internet capability present`() {
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every { mockConnectivityManager.getNetworkCapabilities(mockNetwork) } returns mockCapabilities
        every { mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        val result = repository.isConnected()
        assert(result) { "Expected true when capability is present" }
    }

    @Test fun `isConnected returns false when no active network`() {
        every { mockConnectivityManager.activeNetwork } returns null

        val result = repository.isConnected()
        assert(!result) { "Expected false when there is no active network" }
    }

    @Test fun `isConnected returns false when capability absent`() {
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every { mockConnectivityManager.getNetworkCapabilities(mockNetwork) } returns mockCapabilities
        every { mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns false

        val result = repository.isConnected()
        assert(!result) { "Expected false when capability is absent" }
    }

    @Test fun `observe emits current state and updates on callbacks`()  = runTest {
        every { mockConnectivityManager.activeNetwork } returns mockNetwork
        every { mockConnectivityManager.getNetworkCapabilities(mockNetwork) } returns mockCapabilities
        every { mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        val flow: Flow<Boolean> = repository.observe()

        val callbackSlot = slot<ConnectivityManager.NetworkCallback>()
        verify { mockConnectivityManager.registerDefaultNetworkCallback(capture(callbackSlot)) }
        val callback = callbackSlot.captured

        flow.test {
            assert(awaitItem()) { "Initial state should be true" }

            callback.onLost(mockNetwork)
            assert(!awaitItem()) { "After onLost should be false" }

            callback.onAvailable(mockNetwork)
            assert(awaitItem()) { "After onAvailable should be true" }

            callback.onUnavailable()
            assert(!awaitItem()) { "After onUnavailable should be false" }

            cancelAndIgnoreRemainingEvents()
        }

        verify { mockConnectivityManager.unregisterNetworkCallback(callback) }
    }

}
