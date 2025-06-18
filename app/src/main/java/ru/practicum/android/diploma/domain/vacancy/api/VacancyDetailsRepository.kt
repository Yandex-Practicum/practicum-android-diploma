package ru.practicum.android.diploma.domain.vacancy.api

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetails>
}
