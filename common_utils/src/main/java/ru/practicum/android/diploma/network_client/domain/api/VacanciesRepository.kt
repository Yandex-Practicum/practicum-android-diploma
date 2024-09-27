package ru.practicum.android.diploma.network_client.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network_client.domain.models.Resource

interface VacanciesRepository {
    fun searchVacancies(dto: Any): Flow<Resource<List<>>>
    fun listVacancy(id: String)
    fun listAreas()
    fun listIndustries()
}
