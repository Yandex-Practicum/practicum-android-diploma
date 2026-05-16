package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.Const
import java.io.IOException

class RetrofitNetworkClient(
    private val apiService: ApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesRequest -> apiService.getVacancies(Const.API_TOKEN,dto.toMap())
                    is VacancyDetailsRequest -> apiService.getVacancyDetails(Const.API_TOKEN,dto.id.toString())
                    is AreasRequest -> apiService.getAreas(Const.API_TOKEN)
                    is IndustriesRequest -> apiService.getIndustries(Const.API_TOKEN)
                    else -> return@withContext Response().apply { resultCode = 400 }
                }
                response.apply { resultCode = 200 }
            } catch (e: IOException) {
                Response().apply { resultCode = -1 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
