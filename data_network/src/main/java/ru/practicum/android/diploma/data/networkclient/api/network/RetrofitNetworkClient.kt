package ru.practicum.android.diploma.data.networkclient.api.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.commonutils.NetworkUtils
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HttpStatus
import ru.practicum.android.diploma.data.networkclient.api.dto.Response

class RetrofitNetworkClient(
    private val hhApiService: HHApiService,
    private val context: Context,
) : ru.practicum.android.diploma.data.networkclient.api.NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        Log.d(TAG, "Starting request to HH")
        if (NetworkUtils().isConnected(context)) {
            return object : Response {
                override var resultCode = HttpStatus.NO_INTERNET
            }
        }
        return withContext(Dispatchers.IO) {
            when (dto) {
                is HHApiIndustriesRequest -> industriesRequest(
                    dto
                )

                is HHApiVacanciesRequest -> vacancyListRequest(
                    dto
                )

                is HHApiVacancyRequest -> vacancyRequest(dto)
                is HHApiRegionsRequest -> regionsRequest(dto)
                else -> {
                    Log.e(TAG, "Error is ${dto::class.qualifiedName}")
                    object : Response {
                        override var resultCode = HttpStatus.CLIENT_ERROR
                    }
                }
            }
        }
    }

    private suspend fun regionsRequest(dto: HHApiRegionsRequest): Response {
        val request = if (dto.term.isNullOrEmpty()) null else mapOf(QUERY to dto.term)
        return hhApiService.searchRegions(request)
    }

    private suspend fun vacancyListRequest(dto: HHApiVacanciesRequest): Response {
        val request = mapOf("query" to dto.request)
        return hhApiService.searchVacancies(request)
    }

    private suspend fun vacancyRequest(dto: HHApiVacancyRequest): Response {
        return hhApiService.getVacancy(dto.vacancyId)
    }

    private suspend fun industriesRequest(dto: HHApiIndustriesRequest): Response {
        val request = if (dto.term.isNullOrEmpty()) null else mapOf(QUERY to dto.term)
        return hhApiService.searchIndustries(request)
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val QUERY = "query"
    }
}
