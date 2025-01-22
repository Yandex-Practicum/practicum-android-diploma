package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SearchViewState

interface SearchInteractor {
    fun searchVacancy(text: String, page: Int): Flow<SearchViewState>

}
