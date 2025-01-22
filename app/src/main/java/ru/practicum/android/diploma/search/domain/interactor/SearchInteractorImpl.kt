package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchInteractor {
    override fun searchVacancy(text: String, page: Int): Flow<SearchViewState> {
        val searchQueryParams = SearchQueryParams(
            text = text,
            page = page,
        )
        return searchRepository.searchVacancy(searchQueryParams)
    }
}
