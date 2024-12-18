package ru.practicum.android.diploma.data.dto.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.domain.NetworkClient
import java.io.IOException

class RetrofitNetworkClient(
    private val connectivityManager: ConnectivityManager
) : NetworkClient {

    private val headerClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(headerClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val hhApiService = retrofit.create(HhApi::class.java)

    override suspend fun doRequest(dto: Any): Response {
         return if (!isConnected()) {
            Response().apply { resultCode = -1 }
        } else {
            when (dto) {
                is VacancySearchRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            hhApiService
                                .getVacancies(
                                    dto.searchParams.searchQuery,
                                    dto.searchParams.nameOfCityForFilter,
                                    dto.searchParams.nameOfIndustryForFilter,
                                    dto.searchParams.onlyWithSalary,
                                    dto.searchParams.currencyOfSalary,
                                    dto.searchParams.expectedSalary,
                                    dto.searchParams.numberOfVacanciesOnPage,
                                    dto.searchParams.numberOfPage
                                )
                                .apply { resultCode = HTTP_OK_CODE }
                        } catch (e: HttpException) {
                            when (e.code()) {
                                HTTP_PAGE_NOT_FOUND_CODE -> Response().apply { resultCode = HTTP_PAGE_NOT_FOUND_CODE }
                                else -> Response().apply { resultCode = HTTP_CODE_0 }
                            }
                        } catch (e: IOException) {
                            Log.e("error", "$e")
                            Response().apply { resultCode = HTTP_INTERNAL_SERVER_ERROR_CODE }
                        }
                    }
                }
                else -> {
                    Response().apply { resultCode = HTTP_BAD_REQUEST_CODE }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            false
        }
    }

    companion object {
        private const val BASE_URL = "https://api.hh.ru/"

        const val HTTP_BAD_REQUEST_CODE = 400
        const val HTTP_OK_CODE = 200
        const val HTTP_INTERNAL_SERVER_ERROR_CODE = 500
        const val HTTP_PAGE_NOT_FOUND_CODE = 404
        const val HTTP_CODE_0 = 0
    }
}
