package ru.practicum.android.diploma.common.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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
            Response().apply { resultCode = Response.NO_INTERNET_ERROR_CODE }
        } else {
            if (dto is SearchVacancyRequest) {
                withContext(Dispatchers.IO) {
                    try {
                        val resp = headHunterApi.searchVacancies(mapper.map(dto.expression))
                        resp.apply { resultCode = Response.SUCCESS_RESPONSE_CODE }
                    } catch (e: HttpException) {
                        Log.e("RetrofitNetworkClient", "Unexpected error: ${e.message}", e)
                        Response().apply { resultCode = Response.INTERNAL_SERVER_ERROR_CODE }
                    }
                }
            } else {
                Response().apply { resultCode = Response.BAD_REQUEST_ERROR_CODE }
            }
        }
}
