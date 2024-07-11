package ru.practicum.android.diploma.search.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SERVER_ERROR
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.utils.isInternetAvailable
import ru.practicum.android.diploma.vacancy.data.dto.VacancyRequest

class RetrofitClient(
    private val jobApiService: JobApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isInternetAvailable(context)) {
            return Response().apply { resultCode = RESULT_CODE_NO_INTERNET }

        }
        return getResp(dto = dto)
    }

    private suspend fun getResp(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancyRequest -> {
                        val response = jobApiService.getVacancy(dto.id)
                        response.apply { resultCode = RESULT_CODE_SUCCESS }
                    }

                    is SearchRequest -> {
                        val response = jobApiService.searchVacancies(options = dto.options)
                        response.apply { resultCode = RESULT_CODE_SUCCESS }
                    }

                    else -> {
                        Response().apply { resultCode = RESULT_CODE_BAD_REQUEST }
                    }
                }

            } catch (e: HttpException) {
                println("RetrofitClient error code: ${e.code()} message: ${e.message}")
                Response().apply { resultCode = RESULT_CODE_SERVER_ERROR }
            }
        }
    }
}
