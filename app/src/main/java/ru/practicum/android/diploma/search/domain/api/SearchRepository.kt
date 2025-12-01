package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FiltersParameters
import ru.practicum.android.diploma.search.domain.models.VacanciesPage

interface SearchRepository {
    suspend fun getVacancies(query: String, page: Int, filters: FiltersParameters? = null): Flow<Result<VacanciesPage>>
}
