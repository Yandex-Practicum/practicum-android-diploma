package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancySearchFilter
import ru.practicum.android.diploma.domain.models.VacancySearchResult

interface SearchVacanciesRepository {
    fun searchVacancies(filter: VacancySearchFilter): Flow<VacancySearchResult>
}
