package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.SearchResult
import ru.practicum.android.diploma.core.domain.repository.FiltersRepository
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository

class SearchInteractorImpl(
    private val repository: SearchRepository,
    private val filtersRepository: FiltersRepository,

) : SearchInteractor {
    override val filters: Flow<Filters> = flow {
        filtersRepository.filters.collect {
            emit(it)
        }
    }

    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Filters
    ): Flow<Resource<SearchResult>> {
        return repository.searchVacancies(query, page, perPage, filters)
    }
}
