package ru.practicum.android.diploma.network_client.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network_client.domain.models.Resource

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>)
    fun listVacancy(id: String)
    fun listAreas()
    fun listIndustries()
}
