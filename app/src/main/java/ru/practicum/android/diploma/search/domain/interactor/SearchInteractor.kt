package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState

interface SearchInteractor{
    suspend fun searchVacancy(text: String, page: Int): Flow<SearchViewState>

}
