package ru.practicum.android.diploma.data.networkclient.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.commonutils.NetworkUtils
import ru.practicum.android.diploma.data.networkclient.data.NetworkClient
import ru.practicum.android.diploma.data.networkclient.data.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.data.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.data.dto.Response
import ru.practicum.android.diploma.networkclient.domain.models.HttpStatus

class RetrofitNetworkClient(
    private val hhApiService: ru.practicum.android.diploma.data.networkclient.data.network.HHApiService,
    private val context: Context,
) : ru.practicum.android.diploma.data.networkclient.data.NetworkClient {

    override suspend fun doRequest(dto: Any): ru.practicum.android.diploma.data.networkclient.data.dto.Response {
        Log.d(ru.practicum.android.diploma.data.networkclient.data.network.RetrofitNetworkClient.Companion.TAG, "Starting request to HH")
        if (NetworkUtils().isConnected(context)) {
            return object : ru.practicum.android.diploma.data.networkclient.data.dto.Response {
                override var resultCode = HttpStatus.NO_INTERNET
            }
        }
        return withContext(Dispatchers.IO) {
            when (dto) {
                is ru.practicum.android.diploma.data.networkclient.data.dto.HHApiIndustriesRequest -> industriesRequest(dto)
                is ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacanciesRequest -> vacancyListRequest(dto)
                is ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacancyRequest -> vacancyRequest(dto)
                is ru.practicum.android.diploma.data.networkclient.data.dto.HHApiRegionsRequest -> regionsRequest(dto)
                else -> {
                    Log.e(ru.practicum.android.diploma.data.networkclient.data.network.RetrofitNetworkClient.Companion.TAG, "Error is ${dto::class.qualifiedName}")
                    object : ru.practicum.android.diploma.data.networkclient.data.dto.Response {
                        override var resultCode = HttpStatus.CLIENT_ERROR
                    }
                }
            }
        }
    }

    private suspend fun regionsRequest(dto: ru.practicum.android.diploma.data.networkclient.data.dto.HHApiRegionsRequest): ru.practicum.android.diploma.data.networkclient.data.dto.Response {
        val request = if (dto.term.isNullOrEmpty()) null else mapOf(ru.practicum.android.diploma.data.networkclient.data.network.RetrofitNetworkClient.Companion.QUERY to dto.term)
        return hhApiService.searchRegions(request)
    }

    private suspend fun vacancyListRequest(dto: ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacanciesRequest): ru.practicum.android.diploma.data.networkclient.data.dto.Response {
        val request = mapOf("query" to dto.request)
        return hhApiService.searchVacancies(request)
    }

    private suspend fun vacancyRequest(dto: ru.practicum.android.diploma.data.networkclient.data.dto.HHApiVacancyRequest): ru.practicum.android.diploma.data.networkclient.data.dto.Response {
        return hhApiService.getVacancy(dto.vacancyId)
    }

    private suspend fun industriesRequest(dto: ru.practicum.android.diploma.data.networkclient.data.dto.HHApiIndustriesRequest): ru.practicum.android.diploma.data.networkclient.data.dto.Response {
        val request = if (dto.term.isNullOrEmpty()) null else mapOf(ru.practicum.android.diploma.data.networkclient.data.network.RetrofitNetworkClient.Companion.QUERY to dto.term)
        return hhApiService.searchIndustries(request)
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val QUERY = "query"
    }
}
