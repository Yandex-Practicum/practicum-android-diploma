package ru.practicum.android.diploma.data.vacancysearchscreen.network

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
        return if (isConnected(context) == false) {
            Response().apply { resultCode = NO_CONNECTION }
        } else if (dto !is VacanciesRequest) {
            Response().apply { resultCode = SEARCH_FAILED }
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = service.getVacancies(
                        text = dto.text,
                        page = dto.page,
                        perPage = dto.perPage)
                    response.apply { resultCode = SEARCH_SUCCESS }
                } catch (e: retrofit2.HttpException) {
                    Log.e("Repository", "Search error", e)
                    Response().apply { resultCode = SERVER_ERROR }
                }
            }
        }
    }

    companion object {
        private const val NO_CONNECTION = -1
        private const val SEARCH_SUCCESS = 2
        private const val SEARCH_FAILED = 1
        private const val SERVER_ERROR = 5
    }
}
