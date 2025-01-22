package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState

interface SearchRepository {
    fun searchVacancy(expression: SearchQueryParams): Flow<SearchViewState>
}
