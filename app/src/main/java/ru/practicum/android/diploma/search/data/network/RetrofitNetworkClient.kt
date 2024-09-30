package ru.practicum.android.diploma.search.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.VacancySearchRequest
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.util.network.connectionCheck

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HHApiService
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!connectionCheck(context)) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is VacancySearchRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = hhApiService.searchVacancies(dto.expression)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}
