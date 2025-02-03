package ru.practicum.android.diploma.search.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.sharedprefs.repository.SharedPrefsRepository
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchInteractorImpl(
    private val searchRepository: SearchRepository,
    private val sharedPrefsRepositoryImpl: SharedPrefsRepository
) : SearchInteractor {
    override fun searchVacancy(text: String, page: Int): Flow<SearchViewState> {
        val filter = sharedPrefsRepositoryImpl.getFilter()
        val searchQueryParams = SearchQueryParams(
            text = text,
            page = page,
            filter = filter
        )
        return searchRepository.searchVacancy(searchQueryParams)
    }
}
