package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.SearchResult
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository

class SearchInteractorImpl(private val repository: SearchRepository): SearchInteractor {
    override fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Map<String, String>
    ): Flow<Resource<SearchResult>> {
        return repository.searchVacancies(query, page, perPage, filters)
    }
}
