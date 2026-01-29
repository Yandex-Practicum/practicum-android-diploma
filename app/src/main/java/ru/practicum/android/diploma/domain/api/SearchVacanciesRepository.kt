package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface SearchVacanciesRepository {
    fun searchVacancies(expression: String): Flow<VacancySearchResult>
}
