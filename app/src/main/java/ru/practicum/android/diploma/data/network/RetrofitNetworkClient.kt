package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyResponse

class RetrofitNetworkClient(private val vacancyService: VacancyApi) : NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is Request.VacanciesRequest -> {
                        val resp = vacancyService.searchVacancy(dto.options)
                        resp.apply { resultCode = SuccessfulRequest }
                    }

                    is Request.VacancyRequest -> {
                        val resp = VacancyResponse(vacancyService.getVacancy(dto.id))
                        resp.apply { resultCode = SuccessfulRequest }
                    }
                }
            } catch (e: HttpException) {
                Log.d(
                    RETROFIT_LOG,
                    "${e.message}"
                )
                Response().apply { resultCode = BadRequest }
            }
        }
    }

    companion object {
        private const val RETROFIT_LOG = "RETROFIT_LOG"
        private const val SuccessfulRequest = 200
        private const val BadRequest = 400
    }
}
