package ru.practicum.android.diploma.data.network.api

import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.main.VacancyLongDtoEntity
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponseDto
import ru.practicum.android.diploma.data.network.NetworkChecker
import ru.practicum.android.diploma.data.network.Response

class RetrofitNetworkClient(
    private val api: HeadHunterApi,
    private val networkChecker: NetworkChecker
) : NetworkClient {
    override suspend fun searchVacancies(filters: Map<String, String>): Response<VacancySearchResponseDto> {
        return safeCall(networkChecker) {
            api.searchVacancies(filters)
        }
    }

    override suspend fun getVacancyDetails(id: String): Response<VacancyLongDtoEntity> {
        return safeCall(networkChecker) {
            api.getVacancyDetails(id)
        }
    }

    private suspend inline fun <T> safeCall(
        networkChecker: NetworkChecker,
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
                400 -> Response.BadRequest
                404 -> Response.NotFound
                in 500..599 -> Response.ServerError
                else -> Response.ServerError
            }
        } catch (e: Exception) {
            Response.ServerError
        }
    }
}
