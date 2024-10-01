package ru.practicum.android.diploma.search.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.VacancySearchRequest
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.util.network.connectionCheck
import ru.practicum.android.diploma.util.storage.ResponseCode
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HHApiService
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        var responseCode = Response()
        if (!connectionCheck(context)) {
            responseCode.apply { resultCode = ResponseCode.RESPONSE_NOT_CONNECTED.code }
        }
        if (dto !is VacancySearchRequest) {
            responseCode.apply { resultCode = ResponseCode.RESPONSE_BAD_REQUEST.code }
        } else withContext(Dispatchers.IO) {
            try {
                responseCode = hhApiService.searchVacancies(dto.expression)
                responseCode.apply { resultCode = ResponseCode.RESPONSE_OK.code }
            } catch (e: IOException) {
                responseCode.apply { resultCode = ResponseCode.RESPONSE_INTERNAL_SERVER_ERROR.code }
                println(e)
            }
        }
        return responseCode
    }
}
