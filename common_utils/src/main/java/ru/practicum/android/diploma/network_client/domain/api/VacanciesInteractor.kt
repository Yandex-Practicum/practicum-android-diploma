package ru.practicum.android.diploma.network_client.domain.api

import kotlinx.coroutines.flow.Flow

interface VacanciesInteractor {
    suspend fun searchVacancies(options: Map<String, String>): Flow<String>
    suspend fun listVacancy(id: String)
    suspend fun listAreas()
    suspend fun listIndustries()
}
