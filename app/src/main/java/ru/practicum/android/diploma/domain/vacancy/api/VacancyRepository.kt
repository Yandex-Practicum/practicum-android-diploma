package ru.practicum.android.diploma.domain.vacancy.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.ApiResponse
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyRepository {
    fun getVacancy(id: String): Flow<ApiResponse<Vacancy>>
}
