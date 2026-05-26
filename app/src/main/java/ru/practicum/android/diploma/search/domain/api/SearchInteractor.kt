package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.SearchResult

interface SearchInteractor {
    fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Map<String, String>
    ): Flow<Resource<SearchResult>>
}
