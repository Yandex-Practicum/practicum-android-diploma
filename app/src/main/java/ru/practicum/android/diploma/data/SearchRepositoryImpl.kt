package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.search.SearchResponse
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.filter.Filters
import ru.practicum.android.diploma.util.ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SUCCESS

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider,
    private val mapper: VacancyMapper,
) : SearchRepository {
    override fun searchVacancies(
        query: String,
        filters: Filters,
        pageCount: Int
    ): Flow<Resource<List<Vacancy>>> =
        flow {
            val response = networkClient.doSearchRequest(query, filters, pageCount)
            when (response.resultCode) {
                ERROR -> {
                    emit(Resource.Error(resourceProvider.getString(R.string.no_internet)))
                }

                SUCCESS -> {
                    with(response as SearchResponse) {
                        val vacanciesList = items.map { mapper.mapVacancyFromDto(it, found) }
                        emit(Resource.Success(vacanciesList))
                    }

                }

                else -> {
                    emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
                }
            }
        }
}