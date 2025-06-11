package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.util.NetworkUtils

class RetrofitNetworkClient(
    private val apiService: HHApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!NetworkUtils.isConnected(context)) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchRequest -> {
                        val response = apiService.searchVacancies(dto.searchOptions)
                        response.apply { resultCode = 200 }
                    }

                    is VacancyDetailRequest -> {
                        val response = apiService.getVacancy(dto.id)
                        response.apply { resultCode = 200 }
                    }

                    else -> {
                        Response().apply { resultCode = 400 }
                    }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}
