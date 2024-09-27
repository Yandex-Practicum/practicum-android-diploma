package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow

interface VacanciesInteractor {
    fun searchVacancies(options: Map<String, String>): Flow<String>
    fun listVacancy(id: String): Flow<String>
    fun listAreas(): Flow<String>
    fun listIndustries(): Flow<String>
}
