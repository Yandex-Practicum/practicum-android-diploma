package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.models.vacancies.Response
import ru.practicum.android.diploma.data.models.vacancies.VacanciesApi
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.util.NetworkHelper.isConnected

class RetrofitNetworkClient(private val service: VacanciesApi, private val context: Context) : SearchNetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (isConnected(context) == false) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            when {
                dto !is VacanciesRequest -> Response().apply { resultCode = SEARCH_FAILED }
                else -> try {
                    val response = service.getVacancies(text = dto.text)
                    response.apply { resultCode = SEARCH_SUCCESS }
                } catch (e: retrofit2.HttpException) {
                    Log.e("Repository", "Search error", e)
                    Response().apply { resultCode = SERVER_ERROR }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_SUCCESS = 200
        private const val SEARCH_FAILED = 400
        private const val SERVER_ERROR = 500
    }
}
