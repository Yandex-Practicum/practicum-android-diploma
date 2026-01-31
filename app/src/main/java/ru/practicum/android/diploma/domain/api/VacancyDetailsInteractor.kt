package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetailsSearchResult

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(id: String): VacancyDetailsSearchResult
}
