package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.util.Resource

class SearchVacancyUseCase(
    private val searchVacancyRepository: SearchVacancyRepository
) {
    fun execute(
        searchText: String,
        page: Int,
        filterParameters: SearchFilterParameters
    ): Flow<Resource<SearchVacanciesResult>> {
        return searchVacancyRepository.getVacanciesByPage(
            searchText = searchText,
            page = page,
            filterParameters = filterParameters
        )
    }
}
