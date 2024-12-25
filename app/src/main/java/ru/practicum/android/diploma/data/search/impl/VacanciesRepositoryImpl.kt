package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import ru.practicum.android.diploma.data.dto.request.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.search.models.SearchParams
import java.io.IOException

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    override fun getVacancies(searchParams: SearchParams): Flow<VacancySearchResponse> {
        return flow {
            val response = networkClient.doRequest(VacancySearchRequest(searchParams))
            when (response.code) {
                RetrofitNetworkClient.HTTP_OK_CODE -> emit(response as VacancySearchResponse)

                RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE ->
                    throw createHttpException(RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE, "Not Found")

                RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE ->
                    throw createHttpException(RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE, "Server Error")

                RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE ->
                    throw createHttpException(RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE, "Bad Request")

                RetrofitNetworkClient.HTTP_CODE_0 ->
                    throw createHttpException(RetrofitNetworkClient.HTTP_CODE_0, "Unknown Error")

                -1 ->
                    throw IOException("Network Error")

                else ->

                    throw createHttpException(response.code, "Unexpected Error: ${response.code}")
            }
        }
    }

    private fun createHttpException(code: Int, message: String): HttpException {
        return HttpException(
            Response.error<Any>(
                code,
                ResponseBody.create(null, message)
            )
        )
    }
}
