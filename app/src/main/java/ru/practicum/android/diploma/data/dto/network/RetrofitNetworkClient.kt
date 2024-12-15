package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.domain.NetworkClient
import java.io.IOException

const val ERROR_400 = 400
const val ERROR_200 = 200
const val ERROR_500 = 500
const val ERROR_0 = 0
const val ERROR_404 = 0

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager,
    private val hhService: HhApi,
) : NetworkClient {

    private val headerClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(headerClient)
        .build()

    override suspend fun doRequest(dto: Any): Response {
        val result: Response

        if (!isConnected()) {
            result = Response(-1)
        } else if (dto !is VacancySearchRequest) {
            result = Response(ERROR_400)
        } else {
            result = withContext(Dispatchers.IO) {
                try {
                    val resp = hhService.getVacancies(dto.vacancyName)
                    resp.apply { resultCode = ERROR_200 }
                } catch (e: HttpException) {
                    when (e.code()) {
                        ERROR_404 -> Response(ERROR_0)
                        else -> Response(ERROR_404)
                    }
                } catch (e: IOException) {
                    Log.e("error", "$e")
                    Response(ERROR_500)
                }
            }
        }

        return result
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

    companion object {
        private const val BASE_URL = "https://api.hh.ru/"
    }
}
