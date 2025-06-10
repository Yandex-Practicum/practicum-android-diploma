package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacancyDetailRequest

class RetrofitNetworkClient(
    private val apiService: HHApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchRequest -> {
                        val response = apiService.searchVacancies(dto.searchOptions, dto.token, dto.appName)
                        response.apply { resultCode = 200 }
                    }

                    is VacancyDetailRequest -> {
                        val response = apiService.getVacancy(dto.id, dto.token, dto.appName)
                        response.apply { resultCode = 200 }
                    }

                    else -> {
                        Response().apply { resultCode = 400 }
                    }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
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
