package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.domain.NetworkClient
import java.io.IOException

const val ERROR400 = 400
const val ERROR200 = 200
const val ERROR500 = 500
const val ERROR0 = 0
const val ERROR404 = 0

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager,
    private val hhService: HhApi,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response(-1)
        }

        if (dto !is VacancySearchRequest) {
            return Response(ERROR400)
        }

        return withContext(Dispatchers.IO) {
            try {
                val resp =
                    hhService.getVacancies(dto.vacancyName)
                resp.apply { resultCode = ERROR200 }

            } catch (e: HttpException) {
                when (e.code()) {
                    ERROR404 -> Response(ERROR0)
                    else -> Response(ERROR404)
                }
            } catch (e: IOException) {
                Log.e("error", "$e")
                Response(ERROR500)
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
