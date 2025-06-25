package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.VacanciesSearchRequest
import ru.practicum.android.diploma.data.VacancyDetailRequest
import ru.practicum.android.diploma.data.filters.AreasRequest
import ru.practicum.android.diploma.data.filters.AreasResponse
import ru.practicum.android.diploma.data.filters.IndustriesRequest
import ru.practicum.android.diploma.data.filters.IndustriesResponse
import ru.practicum.android.diploma.data.vacancy.HhApi
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_400_BAD_REQUEST
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION
import ru.practicum.android.diploma.util.NetworkUtils

class NetworkClient(
    private val context: Context,
    private val hhApi: HhApi,
) : NetworkClientInterface {
    override suspend fun doRequest(dto: Any): Response {
        if (!NetworkUtils.isConnected(context)) {
            return Response().apply { resultCode = HTTP_NO_CONNECTION }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacanciesSearchRequest -> responseSearch(dto)
                    is VacancyDetailRequest -> responseDetail(dto)
                    is AreasRequest -> responseAreas()
                    is IndustriesRequest -> responseIndustries()
                    else -> {
                        Response().apply { resultCode = HTTP_400_BAD_REQUEST }
                    }
                }
            } catch (_: Throwable) {
                Response().apply { resultCode = HTTP_500_INTERNAL_SERVER_ERROR }
            }
        }
    }

    private suspend fun responseSearch(dto: VacanciesSearchRequest): Response {
        val response = hhApi.searchVacancies(dto.searchOptions)
        response.apply { resultCode = HTTP_200_OK }
        return response
    }

    private suspend fun responseDetail(dto: VacancyDetailRequest): Response {
        val response = hhApi.getVacancyDetails(dto.id)
        response.apply { resultCode = HTTP_200_OK }
        return response
    }

    private suspend fun responseAreas(): Response {
        return AreasResponse(
            areas = hhApi.getAreas()
        ).apply {
            resultCode = HTTP_200_OK
        }
    }

    private suspend fun responseIndustries(): Response {
        return IndustriesResponse(
            industries = hhApi.getIndustries()
        ).apply { resultCode = HTTP_200_OK }
    }
}
