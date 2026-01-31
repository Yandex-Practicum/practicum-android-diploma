package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetailsSearchResult

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: String): VacancyDetailsSearchResult
}
