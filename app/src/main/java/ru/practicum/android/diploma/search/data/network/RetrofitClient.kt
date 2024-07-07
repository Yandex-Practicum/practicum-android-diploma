package ru.practicum.android.diploma.search.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.search.data.dto.REST_BAD_REQUEST
import ru.practicum.android.diploma.search.data.dto.REST_SERVER_ERROR
import ru.practicum.android.diploma.search.data.dto.REST_SUCCESS
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.dto.Resp
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.utils.isInternetAvailable
import ru.practicum.android.diploma.vacancy.data.dto.VacancyRequest

class RetrofitClient(
    private val jobApiService: JobApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Resp {
        if (!isInternetAvailable(context)) {
            return Resp().apply { resultCode = RESULT_CODE_NO_INTERNET }
        }
        return getResp(dto = dto)
    }

    private suspend fun getResp(dto: Any): Resp {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancyRequest -> {
                        val response = jobApiService.getVacancy(dto.id)
                        response.apply { resultCode = REST_SUCCESS }
                    }

                    is SearchRequest -> {
                        val response = jobApiService.searchVacancies(options = dto.options)
                        response.apply { resultCode = REST_SUCCESS }
                    }

                    else -> {
                        Resp().apply { resultCode = REST_BAD_REQUEST }
                    }
                }

            } catch (e: HttpException) {
                println("RetrofitClient error code: ${e.code()} message: ${e.message}")
                Resp().apply { resultCode = REST_SERVER_ERROR }
            }
        }
    }

}
