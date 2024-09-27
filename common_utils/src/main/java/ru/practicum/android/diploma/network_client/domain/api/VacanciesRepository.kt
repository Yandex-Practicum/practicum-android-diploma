package ru.practicum.android.diploma.network_client.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network_client.domain.models.Resource

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>): Flow<Resource<List<>>>
    fun listVacancy(id: String): Flow<Resource<>>
    fun listAreas(): Flow<>
    fun listIndustries(): Flow<>
}
