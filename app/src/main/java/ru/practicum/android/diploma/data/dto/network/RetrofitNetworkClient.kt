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
const val error400 = 400
const val error200 = 200
const val error500 = 500

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
            return Response(error400)
        }

        return withContext(Dispatchers.IO) {
            try {
                val resp =
                    hhService.getVacancies(dto.vacancyName)
                resp.apply { resultCode = error200 }
            }
            catch (e: Throwable) {
                Response(error500)
            }
        }

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
