package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.domain.NetworkClient

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager,
//    private val hhService: HhApi,
    ) : NetworkClient {

    private val baseURL = "https://api.hh.ru/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val hhService = retrofit.create(HhApi::class.java)

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response(-1)
        }

        if (dto !is VacancySearchRequest) {
            return Response(400)
        }

        return withContext(Dispatchers.IO) {
            try {
                val resp =
                    hhService.getVacancies(dto.vacancyName)
                resp.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response(500)
            }
        }
    }

    private fun isConnected(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            val result = when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
            return result
        }
        return false
    }

}
