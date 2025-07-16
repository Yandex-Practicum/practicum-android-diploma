package ru.practicum.android.diploma.data.vacancysearchscreen.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.models.vacancies.Response
import ru.practicum.android.diploma.data.models.vacancies.VacanciesApi
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsApi
import ru.practicum.android.diploma.data.models.vacancydetails.VacancyDetailsRequest
import ru.practicum.android.diploma.util.NetworkHelper.isConnected

class RetrofitNetworkClient(
    private val service: VacanciesApi,
    private val vacancyService: VacancyDetailsApi,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return if (!isConnected(context)) {
            Response().apply { resultCode = NO_CONNECTION }
        } else {
            when (dto) {
                is VacanciesRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            val response = service.getVacancies(
                                text = dto.text,
                                page = dto.page,
                                perPage = dto.perPage
                            )
                            response.apply { resultCode = REQUEST_SUCCESS }
                        } catch (e: retrofit2.HttpException) {
                            Log.e("Repository", "Search error", e)
                            Response().apply { resultCode = SERVER_ERROR }
                        }
                    }
                }

                is VacancyDetailsRequest -> {
                    withContext(Dispatchers.IO) {
                        try {
                            val response = vacancyService.getVacancyDetails(id = dto.id)
                            response.apply { resultCode = REQUEST_SUCCESS }
                        } catch (e: retrofit2.HttpException) {
                            Log.e("Repository", "Details error", e)
                            Response().apply { resultCode = SERVER_ERROR }
                        }
                    }
                }

                else -> {
                    Response().apply { resultCode = REQUEST_FAILED }
                }
            }
        }
    }

    companion object {
        private const val NO_CONNECTION = -1
        private const val REQUEST_SUCCESS = 2
        private const val REQUEST_FAILED = 1
        private const val SERVER_ERROR = 5
    }
}
