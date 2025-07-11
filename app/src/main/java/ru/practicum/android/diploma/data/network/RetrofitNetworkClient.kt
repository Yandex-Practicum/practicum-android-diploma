package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.models.vacancies.Response
import ru.practicum.android.diploma.data.models.vacancies.VacanciesApi
import ru.practicum.android.diploma.data.models.vacancies.VacanciesRequest
import ru.practicum.android.diploma.util.NetworkHelper
import ru.practicum.android.diploma.util.NetworkHelper.isConnected

class RetrofitNetworkClient(private val service: VacanciesApi, private val context: Context) : SearchNetworkClient {
    override suspend fun  doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is VacanciesRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getVacancies(text = dto.text)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }
}
