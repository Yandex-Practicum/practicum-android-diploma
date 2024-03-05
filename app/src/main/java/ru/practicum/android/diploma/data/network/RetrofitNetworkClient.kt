package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val jobVacancySearchApi: JobVacancySearchApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ResponseCodes.NO_CONNECTION }
        }

        if ((dto !is VacanciesSearchRequest)
            && (dto !is DetailRequest)
            && (dto !is IndustriesRequest)
        ) {
            return Response().apply { resultCode = ResponseCodes.ERROR }
        }

        return withContext(Dispatchers.IO) {
            try {
                Log.d("ResultIndustries", "Retrofit перед запросом")
                val response = when (dto) {
                    is VacanciesSearchRequest -> async { jobVacancySearchApi.getFullListVacancy(dto.queryMap) }
                    is IndustriesRequest -> async { getIndustries() }
                    else -> async { jobVacancySearchApi.getVacancyDetail((dto as DetailRequest).id) }
                }.await()
                response.apply { resultCode = ResponseCodes.SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        }
    }

    private suspend fun getIndustries(): Response {
        return try {
            val industries = jobVacancySearchApi.getAllIndustries()
            if (industries.isNotEmpty()) {
                val firstIndustriesResponse = industries.first()
                IndustriesResponse(
                    id = firstIndustriesResponse.id,
                    name = firstIndustriesResponse.name,
                    industries = firstIndustriesResponse.industries
                ).apply { resultCode = ResponseCodes.SUCCESS }
            } else {
                // Создание экземпляра вашего класса Response с кодом ошибки сервера
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        } catch (e: Exception) {
            // Создание экземпляра вашего класса Response с кодом ошибки сервера
            Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
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
