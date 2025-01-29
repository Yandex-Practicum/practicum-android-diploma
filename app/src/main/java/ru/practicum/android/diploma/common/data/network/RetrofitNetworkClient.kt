package ru.practicum.android.diploma.common.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.dto.SearchVacancyRequest
import ru.practicum.android.diploma.common.util.ConnectivityManager
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsRequest

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val mapper: Mapper,
    private val connectivityManager: ConnectivityManager
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!connectivityManager.isConnected()) {
            return Response().apply { resultCode = Response.NO_INTERNET_ERROR_CODE }
        }

        return when (dto) {
            is SearchVacancyRequest -> executeRequest { headHunterApi.searchVacancies(mapper.map(dto.expression)) }
            is VacancyDetailsRequest -> executeRequest { headHunterApi.getVacancyDetails(dto.vacancyId) }
            else -> Response().apply { resultCode = Response.BAD_REQUEST_ERROR_CODE }
        }
    }

    private suspend fun executeRequest(apiCall: suspend () -> Response): Response =
        withContext(Dispatchers.IO) {
            try {
                apiCall().apply { resultCode = Response.SUCCESS_RESPONSE_CODE }
            } catch (e: HttpException) {
                handleError(e)
            }
        }

    private fun handleError(error: HttpException): Response {
        val errorCode = if (error.code() == Response.NOT_FOUND_ERROR_CODE) {
            Response.NOT_FOUND_ERROR_CODE
        } else {
            Response.INTERNAL_SERVER_ERROR_CODE
        }
        Log.e("RetrofitNetworkClient", "HTTP error: ${error.message}", error)
        return Response().apply { resultCode = errorCode }
    }
}
