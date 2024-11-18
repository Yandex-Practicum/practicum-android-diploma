package ru.practicum.android.diploma.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.search.network.AppAPI
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.data.search.network.VacancySearchRequest
import ru.practicum.android.diploma.data.utils.InternetAccessChecker
import ru.practicum.android.diploma.util.CODE_200
import ru.practicum.android.diploma.util.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.util.RESULT_CODE_ERROR

class RetrofitNetworkClient(
    private val apiService: AppAPI,
    private val internetAccessChecker: InternetAccessChecker
) : NetworkClient {
    private var resultCode: Int = 0

    override suspend fun doRequest(dto: Any): Response {
        if (internetAccessChecker.isConnected()) {
            when (dto) {
                is VacancySearchRequest -> {
                    val response = withContext(Dispatchers.IO) {
                        apiService.search(dto.options)
                    }
                    return response.apply { resultCode = CODE_200 }
                }

                else -> {
                    resultCode = RESULT_CODE_BAD_REQUEST
                }
            }
        } else {
            resultCode = RESULT_CODE_ERROR
        }
        return Response().apply { resultCode }
    }
}
