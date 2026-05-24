package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.detail.GetVacancyDetailsResponse

interface VacancyDetailsRepository {

    suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse
}
