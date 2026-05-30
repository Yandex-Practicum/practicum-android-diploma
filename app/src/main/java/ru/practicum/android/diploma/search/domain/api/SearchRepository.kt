package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.SearchResult

interface SearchRepository {
    fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int,
        filters: Filters
    ): Flow<Resource<SearchResult>>
}
