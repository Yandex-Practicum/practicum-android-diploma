package ru.practicum.android.diploma.domain.vacancy.api

import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

interface SearchVacanciesRepository {
    suspend fun search(text: String): Result<List<Vacancy>>
}
