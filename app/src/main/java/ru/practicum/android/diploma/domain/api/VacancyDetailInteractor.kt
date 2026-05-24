package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse

interface VacancyDetailInteractor {
    suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse
}
