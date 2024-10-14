package ru.practicum.android.diploma.util.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.filters.data.dto.FilterAreasRequest
import ru.practicum.android.diploma.filters.data.dto.FilterIndustriesRequest
import ru.practicum.android.diploma.search.data.network.VacancySearchRequest
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsRequest
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HHApiService
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        var responseCode = Response()

        if (!connectionCheck(context)) {
            responseCode.resultCode = HttpStatusCode.NOT_CONNECTED

        } else if (typeCheckError(dto)) {
            responseCode.resultCode = HttpStatusCode.BAD_REQUEST
        } else {
            withContext(Dispatchers.IO) {
                try {
                    when (dto) {
                        is VacancySearchRequest -> responseCode = hhApiService.searchVacancies(dto.request)
                        is VacancyDetailsRequest -> responseCode = hhApiService.getVacancyDetails(dto.expression)
                        is FilterAreasRequest -> responseCode = hhApiService.getRegions().first()
                        is FilterIndustriesRequest -> responseCode = hhApiService.getIndustries().first()
                    }
                    responseCode.resultCode = HttpStatusCode.OK
                } catch (ioException: IOException) {
                    responseCode.resultCode = HttpStatusCode.INTERNAL_SERVER_ERROR
                    println(ioException)
                }
            }
        }
        return responseCode
    }

    private fun typeCheckError(dto: Any): Boolean {
        return dto !is VacancySearchRequest
            && dto !is VacancyDetailsRequest
            && dto !is FilterAreasRequest
            && dto !is FilterIndustriesRequest
    }
}
