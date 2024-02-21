package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import ru.practicum.android.diploma.core.domain.model.SearchVacanciesResult
import ru.practicum.android.diploma.util.Resource

interface SearchVacancyRepository {
    fun getVacanciesByPage(
        searchText: String,
        page: Int,
        perPage: Int = 20,
        filterParameters: SearchFilterParameters
    ): Flow<Resource<SearchVacanciesResult>>
}
