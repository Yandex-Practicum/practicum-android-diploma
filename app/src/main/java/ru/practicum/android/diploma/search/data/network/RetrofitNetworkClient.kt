package ru.practicum.android.diploma.search.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.util.network.connectionCheck
import ru.practicum.android.diploma.util.storage.RESPONSE_BAD_REQUEST
import ru.practicum.android.diploma.util.storage.RESPONSE_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.storage.RESPONSE_NOT_CONNECTED
import ru.practicum.android.diploma.util.storage.RESPONSE_OK
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsRequest
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HHApiService
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        var responseCode = Response()
        if (!connectionCheck(context)) {
            responseCode.apply { resultCode = RESPONSE_NOT_CONNECTED }
        }
        if (dto !is VacancySearchRequest && dto !is VacancyDetailsRequest) {
            responseCode.apply { resultCode = RESPONSE_BAD_REQUEST }
        } else {
            withContext(Dispatchers.IO) {
                try {
                    if (dto is VacancySearchRequest) responseCode = hhApiService.searchVacancies(dto.request)
                    if (dto is VacancyDetailsRequest) responseCode = hhApiService.getVacancyDetails(dto.expression)

                    responseCode.apply { resultCode = RESPONSE_OK }
                } catch (ioException: IOException) {
                    responseCode.apply { resultCode = RESPONSE_INTERNAL_SERVER_ERROR }
                    println(ioException)
                }
            }
        }
        return responseCode
    }
}
