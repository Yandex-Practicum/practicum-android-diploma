package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

    override suspend fun doRequest(dto: Any): Response<Any> {
        if (!isConnected()) {
            return Response(data = null, resultCode = -1)
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesRequest -> apiService.getVacancies(options = dto.toMap())
                    is VacancyDetailsRequest -> apiService.getVacancyDetails(dto.id)
                    is AreasRequest -> apiService.getAreas()
                    is IndustriesRequest -> apiService.getIndustries()
                    else -> return@withContext Response(data = null, resultCode = 400)
                }
                Response(data = response, resultCode = 200)
            } catch (e: IOException) {
                Response(data = null, resultCode = -1)
            } catch (e: HttpException) {
                Response(data = null, resultCode = e.code())
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
