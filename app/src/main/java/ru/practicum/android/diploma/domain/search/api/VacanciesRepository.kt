package ru.practicum.android.diploma.domain.search.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.VacanciesResponse

interface VacanciesRepository {
    fun getVacancies(): Flow<ApiResponse<VacanciesResponse>>
}
