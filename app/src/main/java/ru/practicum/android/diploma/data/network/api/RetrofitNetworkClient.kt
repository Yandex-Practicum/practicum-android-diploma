package ru.practicum.android.diploma.data.network.api

import android.util.Log
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.main.VacancyLongDto
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponseDto
import ru.practicum.android.diploma.data.network.NetworkChecker
import ru.practicum.android.diploma.data.network.Response
import java.io.IOException

class RetrofitNetworkClient(
    private val api: HeadHunterApi,
    private val networkChecker: NetworkChecker
) : NetworkClient {
    override suspend fun searchVacancies(filters: Map<String, String>): Response<VacancySearchResponseDto> {
        return safeCall {
            api.searchVacancies(filters)
        }
    }

    override suspend fun getVacancyDetails(id: String): Response<VacancyLongDto> {
        return safeCall {
            api.getVacancyDetails(id)
        }
    }

    private suspend inline fun <T> safeCall(
        crossinline apiCall: suspend () -> T
    ): Response<T> {
        return try {
            if (!networkChecker.isNetworkAvailable()) {
                Response.NoConnection
            } else {
                Response.Success(apiCall())
            }
        } catch (e: HttpException) {
            when (e.code()) {
                HTTP_BAD_REQUEST -> Response.BadRequest
                HTTP_NOT_FOUND -> Response.NotFound
                in HTTP_INTERNAL_ERROR..HTTP_INTERNAL_ERROR_MAX -> {
                    Log.e(TAG, "Server error: ${e.message}", e)
                    Response.ServerError
                }

                else -> {
                    Log.e(TAG, "Unexpected HTTP error: ${e.message}", e)
                    Response.ServerError
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error: ${e.message}", e)
            Response.ServerError
        }
    }

    private companion object {
        const val TAG = "RetrofitNetworkClient"
        const val HTTP_BAD_REQUEST = 400
        const val HTTP_NOT_FOUND = 404
        const val HTTP_INTERNAL_ERROR = 500
        const val HTTP_INTERNAL_ERROR_MAX = 599
    }
}
