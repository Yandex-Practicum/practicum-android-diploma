package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_400_BAD_REQUEST
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION
import ru.practicum.android.diploma.util.NetworkUtils

class RetrofitNetworkClient(
    private val apiService: HHApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!NetworkUtils.isConnected(context)) {
            return Response().apply { resultCode = HTTP_NO_CONNECTION }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchRequest -> {
                        val response = apiService.searchVacancies(dto.searchOptions)
                        response.apply { resultCode = HTTP_200_OK }
                    }

                    is VacancyDetailRequest -> {
                        val response = apiService.getVacancy(dto.id)
                        response.apply { resultCode =  HTTP_200_OK }
                    }

                    else -> {
                        Response().apply { resultCode = HTTP_400_BAD_REQUEST }
                    }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = HTTP_500_INTERNAL_SERVER_ERROR }
            }
        }
    }
}
