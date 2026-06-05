package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome

interface SearchInteractor {
    suspend fun searchVacancies(
        query: String,
        page: Int = 0,
        filterParameters: FilterParameters
    ): SearchVacanciesOutcome
}
