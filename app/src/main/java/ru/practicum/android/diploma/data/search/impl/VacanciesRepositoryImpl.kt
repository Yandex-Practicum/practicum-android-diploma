package ru.practicum.android.diploma.data.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
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
            when (response.resultCode) {
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as VacancySearchResponse) {
                        emit(this)
                    }
                }
                RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> {
                    throw HttpException(
                        Response.error<Any>(
                            RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE,
                            ResponseBody.create(null, "Not Found")
                        )
                    )
                }
                RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> {
                    throw HttpException(
                        Response.error<Any>(
                            RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE,
                            ResponseBody.create(null, "Server Error")
                        )
                    )
                }
                RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> {
                    throw HttpException(
                        Response.error<Any>(
                            RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE,
                            ResponseBody.create(null, "Bad Request")
                        )
                    )
                }
                RetrofitNetworkClient.HTTP_CODE_0 -> {
                    throw HttpException(
                        Response.error<Any>(
                            RetrofitNetworkClient.HTTP_CODE_0,
                            ResponseBody.create(null, "Unknown Error")
                        )
                    )
                }
                -1 -> {
                    throw IOException("Network Error")
                }
                else -> {
                    throw HttpException(
                        Response.error<Any>(
                            response.resultCode,
                            ResponseBody.create(null, "Unexpected Error: ${response.resultCode}")
                        )
                    )
                }
            }
        }
    }
}
