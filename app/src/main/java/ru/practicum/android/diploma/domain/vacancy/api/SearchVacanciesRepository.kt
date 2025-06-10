package ru.practicum.android.diploma.domain.vacancy.api

import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail

interface SearchVacanciesRepository {
    suspend fun search(text: String): Result<List<VacancyDetail>>
}
