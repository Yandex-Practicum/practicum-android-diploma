package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.FilterAreaRequest
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.networkConnectivityChecker

class RetrofitNetworkClient(
    private val apiService: PracticumApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun filterAreaRequest(dto: Any): Response {
        if (!networkConnectivityChecker(context) || dto !is FilterAreaRequest) {
            return Response().apply {
                resultCode = if (!networkConnectivityChecker(context)) -1 else 400
            }
        }

        return withContext(Dispatchers.IO) {
            try {
                apiService.getAreas().apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}
