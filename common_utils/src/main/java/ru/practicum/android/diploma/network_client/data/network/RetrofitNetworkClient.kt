package ru.practicum.android.diploma.network_client.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.network_client.data.NetworkClient
import ru.practicum.android.diploma.network_client.data.dto.Response

class RetrofitNetworkClient(
    private val hhApiService: HHApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        Log.d(TAG, "Starting request to HH")
        return withContext(Dispatchers.IO) {
            try {
                val response = hhApiService.searchVacancies(dto)
            }
        }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
    }
}
