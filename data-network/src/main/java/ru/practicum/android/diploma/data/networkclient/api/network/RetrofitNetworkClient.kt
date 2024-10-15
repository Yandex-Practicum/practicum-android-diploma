package ru.practicum.android.diploma.data.networkclient.api.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.network.HttpStatus
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.isConnected
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiIndustriesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiRegionsRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacanciesRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacancyRequest

private const val TAG = "RetrofitNetworkClient"
private const val HTTP_CODE_404 = 404
private const val HTTP_CODE_500 = 500
private const val HTTP_CODE_599 = 599

internal class RetrofitNetworkClient(
    private val hhApiService: HHApiService,
    private val context: Context,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        Log.d(TAG, "Starting request to HH")
        if (!context.isConnected()) {
            return object : Response {
                override var resultCode = HttpStatus.NO_INTERNET
            }
        }
        return withContext(Dispatchers.IO) {
            runCatching {
                when (dto) {
                    is HHApiIndustriesRequest -> industriesRequest()
                    is HHApiVacanciesRequest -> vacancyListRequest(dto)
                    is HHApiVacancyRequest -> vacancyRequest(dto)
                    is HHApiRegionsRequest -> regionsRequest()
                    else -> throw IllegalArgumentException("Error is ${dto::class.qualifiedName}")
                }
            }.fold(
                onSuccess = { response ->
                    response.apply { resultCode = HttpStatus.OK }
                },
                onFailure = { e ->
                    Utils.outputStackTrace(TAG, e)
                    val modCodeHTTP = modifiedCodeHTTP(e)
                    object : Response {
                        override var resultCode = modCodeHTTP
                    }
                }
            )
        }
    }

    private suspend fun modifiedCodeHTTP(exception: Throwable): HttpStatus {
        return if (exception is HttpException) {
            when (exception.code()) {
                HTTP_CODE_404 -> {
                    HttpStatus.CLIENT_ERROR_404
                }
                in HTTP_CODE_500..HTTP_CODE_599 -> {
                    HttpStatus.SERVER_ERROR
                }
                else -> {
                    HttpStatus.CLIENT_ERROR
                }
            }
        } else {
            HttpStatus.CLIENT_ERROR
        }
    }

    private suspend fun regionsRequest(): Response {
        val regions = hhApiService.searchRegions()
        Log.d(TAG, regions.toString())
        return regions
    }

    private suspend fun vacancyListRequest(dto: HHApiVacanciesRequest): Response {
        Log.d(TAG, dto.request.toString())
        return hhApiService.searchVacancies(dto.request)
    }

    private suspend fun vacancyRequest(dto: HHApiVacancyRequest): Response {
        val vacancy = hhApiService.getVacancy(dto.vacancyId)
        Log.d(TAG, vacancy.toString())
        return vacancy
    }

    private suspend fun industriesRequest(): Response {
        val industries = hhApiService.searchIndustries()
        Log.d(TAG, industries.toString())
        return industries
    }
}
