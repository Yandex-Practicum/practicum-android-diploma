package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FiltersParameters
import ru.practicum.android.diploma.search.domain.models.VacanciesPage

interface SearchInteractor {
    suspend fun getVacancies(query: String, page: Int = 0, filters: FiltersParameters? = null): Flow<Result<VacanciesPage>>
}
