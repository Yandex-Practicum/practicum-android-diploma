package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.IndustriesResponse
import ru.practicum.android.diploma.common.data.dto.IndustryRequest
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class FilterRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
    private val mapper: Mapper,
) : FilterRepository {
    override fun getIndustries(): Flow<IndustryViewState> = flow {
        val response = networkClient.doRequest(IndustryRequest)
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = (response as IndustriesResponse).result
                if (result.isEmpty()) {
                    emit(IndustryViewState.NotFoundError)
                } else {
                    val data = mapper.map(response)
                    emit(IndustryViewState.Success(data))
                }
            }
            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(IndustryViewState.NotFoundError)
            }
            Response.NO_INTERNET_ERROR_CODE -> {
                emit(IndustryViewState.ConnectionError)
            } else -> {
                emit(IndustryViewState.ServerError)
            }
        }
    }.flowOn(Dispatchers.IO)
}
