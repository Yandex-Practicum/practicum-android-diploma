package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.vacancy.vacancydetails.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.vacancy.vacanysearch.VacancySearchResponseDto
import ru.practicum.android.diploma.util.AppException
import ru.practicum.android.diploma.util.isInternetAvailable
import java.io.IOException

class RetrofitNetworkClient(
    private val context: Context,
    private val apiService: HhApiService
) : NetworkClient {

    override suspend fun detailsVacancyRequest(requestId: String): Result<VacancyDetailsDto> {
        return executeRequest { apiService.getVacancyDetails(requestId) }
    }

    override suspend fun vacanciesSearchRequest(requestQuery: Map<String, String>): Result<VacancySearchResponseDto> {
        return executeRequest { apiService.searchVacancies(requestQuery) }
    }

    private suspend fun <T> executeRequest(block: suspend () -> T): Result<T> {
        if (!isInternetAvailable(context)) {
            return Result.failure(AppException.NoInternetConnection())
        }
        return try {
            Result.success(block())
        } catch (e: IOException) {
            Log.e(NETWORK_TAG, "IO error", e)
            Result.failure(AppException.NoInternetConnection())
        } catch (e: HttpException) {
            Log.e(NETWORK_TAG, "HTTP error ${e.code()}", e)
            Result.failure(
                when (e.code()) {
                    NOT_FOUND_CODE -> AppException.NotFound()
                    in SERVER_ERROR_CODE_MIN..SERVER_ERROR_CODE_MAX -> AppException.ServerError()
                    else -> AppException.UnknownException()
                }
            )
        } catch (a: AppException.UnknownException) {
            Log.e(NETWORK_TAG, "Unexpected error", a)
            Result.failure(AppException.UnknownException())
        }
    }

    companion object {
        private const val NETWORK_TAG = "Network"
        private const val NOT_FOUND_CODE = 404
        private const val SERVER_ERROR_CODE_MIN = 500
        private const val SERVER_ERROR_CODE_MAX = 599
    }
}
