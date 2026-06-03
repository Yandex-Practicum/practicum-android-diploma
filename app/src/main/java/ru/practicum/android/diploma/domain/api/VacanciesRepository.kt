package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome

interface VacanciesRepository {
    suspend fun searchVacancies(searchText: String,
                                page: Int = 0,
                                area: Int? = null,
                                industry: Int? = null,
                                salary: Int? = null,
                                onlyWithSalary: Boolean = false): SearchVacanciesOutcome
}
