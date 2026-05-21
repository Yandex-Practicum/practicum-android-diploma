package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacanciesSearchResult

interface VacanciesRepository {
    suspend fun searchVacancies(searchText: String, page: Int = 0): VacanciesSearchResult?
}
