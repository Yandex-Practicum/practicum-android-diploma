package ru.practicum.android.diploma.common.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.dto.SearchVacancyRequest
import ru.practicum.android.diploma.common.util.ConnectivityManager

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val mapper: Mapper,
    private val connectivityManager: ConnectivityManager
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response =
        if (!connectivityManager.isConnected()) {
            Response().apply { resultCode = NO_INTERNET_ERROR_CODE }
        } else {
            if (dto is SearchVacancyRequest) {
                withContext(Dispatchers.IO) {
                    try {
                        val resp = headHunterApi.searchVacancies(mapper.map(dto.expression))
                        resp.apply { resultCode = SUCCESS_RESPONSE_CODE }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = INTERNAL_SERVER_ERROR_CODE }
                    }
                }
            } else {
                Response().apply { resultCode = BAD_REQUEST_ERROR_CODE }
            }
        }

    companion object {
        const val NO_INTERNET_ERROR_CODE = -1
        const val SUCCESS_RESPONSE_CODE = 200
        const val BAD_REQUEST_ERROR_CODE = 400
        const val INTERNAL_SERVER_ERROR_CODE = 500
    }

}
