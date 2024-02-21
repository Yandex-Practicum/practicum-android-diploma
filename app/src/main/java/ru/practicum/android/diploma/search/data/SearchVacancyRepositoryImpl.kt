package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.core.data.network.dto.SearchVacanciesResponse
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchVacancyRepository {
    override fun getVacanciesByPage(
        searchText: String,
        page: Int,
        perPage: Int,
        filterParameters: SearchFilterParameters
    ): Flow<Resource<SearchVacanciesResult>> = flow {
        val response = networkClient.getVacanciesByPage(searchText, filterParameters, page, perPage)
        when (response.resultCode) {
            RetrofitNetworkClient.SUCCESSFUL_CODE -> {
                val searchVacanciesResponse = response as SearchVacanciesResponse
                val domainModel = SearchVacanciesResult(
                    numOfResults = searchVacanciesResponse.numOfResults,
                    vacancies = searchVacanciesResponse.vacancies.map { VacancyMapper.mapToDomain(it) }
                )
                emit(Resource.Success(data = domainModel))
            }

            RetrofitNetworkClient.NETWORK_ERROR_CODE -> {
                emit(Resource.InternetError())
            }

            RetrofitNetworkClient.EXCEPTION_ERROR_CODE -> {
                emit(Resource.ServerError())
            }
        }
    }
}
