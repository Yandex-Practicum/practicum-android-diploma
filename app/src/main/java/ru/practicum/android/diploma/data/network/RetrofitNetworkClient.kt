package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyRequest

class RetrofitNetworkClient(private val vacancyService: VacancyApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                if (dto is VacancyRequest)  {
                    val resp = vacancyService.searchVacancy(dto.options)
                    resp.apply { resultCode= 200 }
                } else {
                    Response().apply { resultCode = BadRequest }
                }
            } catch (e: Exception) {
                Log.d(
                    "RETROFIT_LOG",
                    "${e.message}"
                )
                Response().apply { resultCode = BadRequest }
            }
        }
    }

    companion object {
        const val BadRequest = 400
    }
}
