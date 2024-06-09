package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.CountriesRequest
import ru.practicum.android.diploma.data.dto.CountriesResponse
import ru.practicum.android.diploma.data.dto.CurrencyRequest
import ru.practicum.android.diploma.data.dto.CurrencyResponse
import ru.practicum.android.diploma.data.dto.IndustryRequest
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import java.io.IOException

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_RESULT_CODE }
        }
        return when (dto) {
            is SearchRequest -> {
                withContext(Dispatchers.IO) {
                    doSearchRequest(dto.options)
                }
            }

            is CurrencyRequest -> {
                withContext(Dispatchers.IO) {
                    doCurrencyRequest()
                }
            }

            is VacancyDetailsRequest -> {
                withContext(Dispatchers.IO) {
                    doVacancyDetailsRequest(dto.vacancyId)
                }
            }

            is IndustryRequest -> {
                withContext(Dispatchers.IO) {
                    doIndustryRequest()
                }
            }

            is CountriesRequest -> {
                withContext(Dispatchers.IO) {
                    doCountriesRequest()
                }
            }

            else -> Response().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doVacancyDetailsRequest(vacancyId: String): Response {
        try {
            val response =
                headHunterApi.getVacancyDetails(BEARER_TOKEN, vacancyId)
            return VacancyDetailsResponse(response.body()).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return VacancyDetailsResponse(null).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doCurrencyRequest(): Response {
        try {
            val response =
                headHunterApi.getCurrencies(BEARER_TOKEN)
            return response.apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return CurrencyResponse(null).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doSearchRequest(options: Map<String, String>): Response {
        try {
            val response =
                headHunterApi.searchVacancies(BEARER_TOKEN, options)
            return SearchResponse(
                response.items,
                response.page,
                response.pages,
                response.found
            ).apply { resultCode = CLIENT_SUCCESS_RESULT_CODE }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return SearchResponse(null, 0, 0, 0).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doIndustryRequest(): Response {
        try {
            val response = headHunterApi.getIndustries()
            return IndustryResponse().apply {
                resultCode = CLIENT_SUCCESS_RESULT_CODE
                items = response.body()!!
            }
        } catch (e: IOException) {
            Log.e(NETWORK_ERROR, e.toString())
            return IndustryResponse().apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private suspend fun doCountriesRequest(): Response {
        try {
            val countries = headHunterApi.getCountries()
            return CountriesResponse(countries.body()).apply {
                resultCode = CLIENT_SUCCESS_RESULT_CODE
            }
        } catch (e: HttpException) {
            Log.e(NETWORK_ERROR, e.toString())
            return CountriesResponse(listOf()).apply { resultCode = CLIENT_ERROR_RESULT_CODE }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            )
    }

    companion object {
        const val CLIENT_ERROR_RESULT_CODE = 400
        const val CLIENT_SUCCESS_RESULT_CODE = 200
        const val NO_INTERNET_RESULT_CODE = -1
        const val NETWORK_ERROR = "NETWORK ERROR"
        const val BEARER_TOKEN = "Bearer " + BuildConfig.HH_ACCESS_TOKEN
    }
}
