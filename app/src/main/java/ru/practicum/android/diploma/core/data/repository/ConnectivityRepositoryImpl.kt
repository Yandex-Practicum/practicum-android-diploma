package ru.practicum.android.diploma.core.data.repository

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import ru.practicum.android.diploma.core.domain.repository.ConnectivityRepository

class ConnectivityRepositoryImpl(val connectivityManager: ConnectivityManager) : ConnectivityRepository {
    override fun isConnected(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun observe(): Flow<Boolean> {
        val networkState = MutableStateFlow(isConnected())
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                networkState.value = true
            }

            override fun onLost(network: Network) {
                networkState.value = false
            }

            override fun onUnavailable() {
                networkState.value = false
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        return networkState
            .onCompletion {
                connectivityManager.unregisterNetworkCallback(callback)
            }
    }
}
