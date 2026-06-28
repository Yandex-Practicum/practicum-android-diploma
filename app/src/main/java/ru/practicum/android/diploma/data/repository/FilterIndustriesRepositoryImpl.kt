package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class FilterIndustriesRepositoryImpl(private var networkClient: NetworkClient) : FilterIndustriesRepository {
    override fun getIndustries(): Flow<List<Industry>> = flow {
 //       val data = networkClient.filterIndustryRequest.(FilterIndustryDto(FilterIndustriesResponse()).toModel())//??
        //  emit(ApiResult.Loading)

//        val response = networkClient.filterIndustryRequest(FilterIndustriesRequest())
//            if (response.resultCode == SUCCESS_CODE && response is FilterIndustriesResponse) {
//                emit(ApiResult.Success(response..toModel()))
//            } else {
//                emit(ApiResult.Error(response.resultCode))
//            }
//        }
    }
}
