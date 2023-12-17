package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.util.NetworkResult
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.model.SearchQuery
import ru.practicum.android.diploma.search.domain.model.VacancyListData

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override fun searchVacanciesPaged(
        searchQuery: SearchQuery
    ): Flow<NetworkResult<VacancyListData>> {
        return repository.searchVacanciesPaged(searchQuery)
    }

    override fun getSimilarVacancies(vacancyId: String): Flow<NetworkResult<VacancyListData>> {
        return repository.getSimilarVacancies(vacancyId)
    }

    override fun isFilterActive(): Boolean {
        return repository.isFilterActive()
    }
}