package ru.practicum.android.diploma.data.dto.industries.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.dto.request.IndustriesRequest
import ru.practicum.android.diploma.data.dto.response.IndustriesResponse
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient
) : IndustriesRepository {
    override fun getIndustries(): Flow<Resource<List<IndustriesFullDto>>> {
        return flow {
            val response = networkClient.doRequest(IndustriesRequest())
            when (response.code) {
                RetrofitNetworkClient.INTERNET_NOT_CONNECT -> emit(Resource.Error(VacancyError.NETWORK_ERROR))
                RetrofitNetworkClient.HTTP_CODE_0 -> emit(Resource.Error(VacancyError.UNKNOWN_ERROR))
                RetrofitNetworkClient.HTTP_BAD_REQUEST_CODE -> emit(Resource.Error(VacancyError.BAD_REQUEST))
                RetrofitNetworkClient.HTTP_PAGE_NOT_FOUND_CODE -> emit(Resource.Error(VacancyError.NOT_FOUND))
                RetrofitNetworkClient.HTTP_INTERNAL_SERVER_ERROR_CODE -> emit(Resource.Error(VacancyError.SERVER_ERROR))
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    val industriesResponse = response as IndustriesResponse
                    emit(Resource.Success(industriesResponse.industries)) // Эмитируем данные
                }
                else -> emit(Resource.Error(VacancyError.SERVER_ERROR))
            }
        }
    }
}
