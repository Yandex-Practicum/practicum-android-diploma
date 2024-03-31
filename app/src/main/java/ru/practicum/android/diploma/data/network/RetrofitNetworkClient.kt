package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.vacancies.details.DetailRequest
import ru.practicum.android.diploma.data.vacancies.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancies.response.Response
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes

class RetrofitNetworkClient(
    private val context: Context,
    private val searchVacanciesApi: SearchVacanciesApi
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return createNoConnectionResponse()
        }

        return executeRequest(dto)
    }

    private suspend fun createNoConnectionResponse(): Response {
        return Response().apply { resultCode = ResponseCodes.NO_CONNECTION }
    }

    private fun isValidDto(dto: Any): Boolean {
        return dto is VacanciesSearchRequest || dto is DetailRequest
    }

    private suspend fun executeRequest(dto: Any): Response {

        if (!isValidDto(dto)) {
            return createErrorResponse()
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacanciesSearchRequest -> async {
                        searchVacanciesApi.getListVacancy(dto.queryMap)
                    }
                    else -> async { searchVacanciesApi.getVacancyDetail((dto as DetailRequest).id) }
                }.await()
                response.apply { resultCode = ResponseCodes.SUCCESS }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCodes.SERVER_ERROR }
            }
        }
    }

    private fun createErrorResponse(): Response {
        return Response().apply { resultCode = ResponseCodes.ERROR }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return capabilities?.run {
            return hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
