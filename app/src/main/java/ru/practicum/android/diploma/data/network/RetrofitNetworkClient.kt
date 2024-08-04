package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.data.dto.RESULT_CODE_SERVER_ERROR
import ru.practicum.android.diploma.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.data.dto.Responce
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.VacancyRequest

class RetrofitNetworkClient(
    private val hhApiService: HHApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Responce {
        if (!isInternetAvailable(context)) {
            return Responce().apply { resultCode = RESULT_CODE_NO_INTERNET }
        }
        return getResponce(dto = dto)
    }

    private suspend fun getResponce(dto: Any): Responce {
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancyRequest -> {
                        val response = hhApiService.getVacancy(dto.id)
                        response.apply { resultCode = RESULT_CODE_SUCCESS }
                    }

                    is SearchRequest -> {
                        val response = hhApiService.searchVacancies(options = dto.options)
                        response.apply { resultCode = RESULT_CODE_SUCCESS }
                    }

                    else -> {
                        Responce().apply { resultCode = RESULT_CODE_BAD_REQUEST }
                    }
                }

            } catch (e: HttpException) {
                println("RetrofitClient error code: ${e.code()} message: ${e.message}")
                Responce().apply { resultCode = RESULT_CODE_SERVER_ERROR }
            }
        }
    }

}
