package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import java.io.IOException

class RetrofitNetworkClient(
    private val apiService: ApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response<out Any> {
        if (!isConnected()) {
            return Response(data = null, resultCode = -1)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesRequest -> apiService.getVacancies(dto.toMap())
                    is VacancyDetailsRequest -> apiService.getVacancyDetails(dto.id)
                    is AreasRequest -> apiService.getAreas()
                    is IndustriesRequest -> apiService.getIndustries()
                    else -> null
                }
                if (response == null) {
                    Response(data = null, resultCode = 400)
                } else {
                    Response(data = response, resultCode = 200)
                }
            } catch (e: IOException) {
                Log.w(TAG, "Network request failed", e)
                Response(data = null, resultCode = -1)
            } catch (e: HttpException) {
                Response(data = null, resultCode = e.code())
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE,
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            )
    }

    private companion object {
        const val TAG = "RetrofitNetworkClient"
    }
}
