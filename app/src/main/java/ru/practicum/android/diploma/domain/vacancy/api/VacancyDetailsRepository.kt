package ru.practicum.android.diploma.domain.vacancy.api

import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDetailsRepository {
    suspend fun getVacancyDetails(id: String): ApiResponse<VacancyDetail>
}
