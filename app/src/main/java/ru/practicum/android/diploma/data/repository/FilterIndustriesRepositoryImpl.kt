package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.dto.FilterIndustriesRequest
import ru.practicum.android.diploma.data.dto.FilterIndustriesResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.repository.VacancyRepositoryImpl.Companion.SUCCESS_CODE
import ru.practicum.android.diploma.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Vacancy

class FilterIndustriesRepositoryImpl(private var networkClient: NetworkClient): FilterIndustriesRepository() {
    override fun getIndustries(): Flow<List<Industry>> = flow {

          //  emit(ApiResult.Loading)

        val response = networkClient.filterIndustryRequest(FilterIndustriesRequest())
            if (response.resultCode == SUCCESS_CODE && response is FilterIndustriesResponse) {
                emit(ApiResult.Success(response..toModel()))
            } else {
                emit(ApiResult.Error(response.resultCode))
            }
        }
    }
}
