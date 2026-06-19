package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.dto.VacanciesRequest
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult

class VacancyRepositoryImpl(private var networkClient: NetworkClient) : VacanciesRepository {
    override fun searchVacancies(
        query: String,
        page: Int
    ): Flow<ApiResult<VacanciesSearchResult>> = flow {
        emit(ApiResult.Loading)

        val data = networkClient.searchVacancies(VacanciesRequest(query, page))
        if (data.resultCode == SUCCESS_CODE && data is VacanciesResponse) {
            emit(ApiResult.Success(data.toModel()))
        } else {
            emit(ApiResult.Error(data.resultCode))
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
