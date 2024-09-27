package ru.practicum.android.diploma.data.networkclient.domain.api

import kotlinx.coroutines.flow.Flow

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>): Flow<Unit>
    fun listVacancy(id: String): Flow<Unit>
    fun listAreas(): Flow<Unit>
    fun listIndustries(): Flow<Unit>
}
