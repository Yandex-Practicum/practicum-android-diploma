package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.domain.NetworkClient

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager,
    private val hhService: HhApi,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()){
            return Response(-1)
        }
        TODO("Not yet implemented")
    }

    private fun isConnected(): Boolean {

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

}
