package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacancyDescriptionRequest

class RetrofitNetworkClient(private val vacancyService: VacancyApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesRequest -> {
                        val resp = vacancyService.searchVacancy(dto.options)
                        resp.apply { resultCode = SuccessfulRequest }
                    }

                    is VacancyDescriptionRequest -> {
                        val resp = vacancyService.getVacancy(dto.id)
                        resp.apply { resultCode = SuccessfulRequest }
                    }

                    else -> {
                        Response().apply { resultCode = BadRequest }
                    }
                }
            } catch (e: HttpException) {
                Log.d(
                    "RETROFIT_LOG",
                    "${e.message}"
                )
                Response().apply { resultCode = BadRequest }
            }
        }
    }

    companion object {
        const val SuccessfulRequest = 200
        const val BadRequest = 400
    }
}
