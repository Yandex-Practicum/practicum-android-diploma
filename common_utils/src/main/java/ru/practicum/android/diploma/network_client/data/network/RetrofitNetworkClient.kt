package ru.practicum.android.diploma.network_client.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.commonutils.NetworkUtils
import ru.practicum.android.diploma.network_client.data.NetworkClient
import ru.practicum.android.diploma.network_client.data.dto.HHApiIndustriesRequest
import ru.practicum.android.diploma.network_client.data.dto.HHApiRegionsRequest
import ru.practicum.android.diploma.network_client.data.dto.HHApiVacanciesRequest
import ru.practicum.android.diploma.network_client.data.dto.HHApiVacancyRequest
import ru.practicum.android.diploma.network_client.data.dto.Response

class RetrofitNetworkClient(
    private val hhApiService: HHApiService,
    private val context: Context,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        Log.d(TAG, "Starting request to HH")
        if (NetworkUtils().isConnected(context)) {
            return object : Response {
                override var resultCode = -1
            }
        }
        if ((dto !is HHApiIndustriesRequest) && (dto !is HHApiVacanciesRequest) && (dto !is HHApiVacancyRequest) && (dto !is HHApiRegionsRequest)) {
            Log.e(TAG, "Error is ${dto::class.qualifiedName}")
            return object : Response {
                override var resultCode = 400
            }
        }

        return withContext(Dispatchers.IO) {

            when (dto) {
                is HHApiRegionsRequest -> {
                    try {
                        val request = if (dto.term.isNullOrEmpty()) null else mapOf("query" to dto.term)
                        val response = hhApiService.searchRegions(request)
                        response
                    } catch (e: Throwable) {
                        Log.e(TAG, e.message ?: "")
                        Log.e(TAG, e.stackTrace.toString())
                        object : Response {
                            override var resultCode = 500
                        }
                    }
                }

                is HHApiVacancyRequest -> {
                    try {
                        val response = hhApiService.getVacancy(dto.vacancy_id)
                        response
                    } catch (e: Throwable) {
                        Log.e(TAG, e.message ?: "")
                        Log.e(TAG, e.stackTrace.toString())
                        object : Response {
                            override var resultCode = 500
                        }
                    }
                }

                is HHApiVacanciesRequest -> {
                    try {
                        val request = mapOf("query" to dto.request)
                        val response = hhApiService.searchVacancies(request)
                        response
                    } catch (e: Throwable) {
                        Log.e(TAG, e.message ?: "")
                        Log.e(TAG, e.stackTrace.toString())
                        object : Response {
                            override var resultCode = 500
                        }
                    }
                }

                is HHApiIndustriesRequest -> {
                    try {
                        val request = if (dto.term.isNullOrEmpty()) null else mapOf("query" to dto.term)
                        val response = hhApiService.searchIndustries(request)
                        response
                    } catch (e: Throwable) {
                        Log.e(TAG, e.message ?: "")
                        Log.e(TAG, e.stackTrace.toString())
                        object : Response {
                            override var resultCode = 500
                        }
                    }
                }

                else -> {
                    object : Response {
                        override var resultCode = 400
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
    }
}
