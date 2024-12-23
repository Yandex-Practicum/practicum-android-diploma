package ru.practicum.android.diploma.data.dto.industries.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient
) : IndustriesRepository {
    override fun getIndustries(): Flow<Resource<IndustriesResponse>> {
        return flow {
            val response = networkClient.doRequest(IndustriesRequest())
            when (response.code) {
                RetrofitNetworkClient.INTERNET_NOT_CONNECT -> Resource.Error("Network Error")
                RetrofitNetworkClient.HTTP_CODE_0 -> Resource.Error("Unknown Error")
                RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> Resource.Error("Bad Request")
                RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> Resource.Error("Not Found")
                RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> Resource.Error("Server Error")
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as IndustriesResponse) {
                        Resource.Success(response.industries)
                    }
                }

                else -> {
                    Resource.Error("Server Error")
                }
            }
        }
    }
}
