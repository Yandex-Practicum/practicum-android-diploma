package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow

interface IVacancyRepository {
    suspend fun searchVacancies(expression: String): Flow<Resource<List<Vacancy>>>
    suspend fun getCountries(expression: String): Flow<Resource<List<Area>>>
    suspend fun getRegion(expression: String): Flow<Resource<List<Area>>>
    suspend fun getIndustries(expression: String): Flow<Resource<List<Industry>>>
    suspend fun getVacancyDetails(expression: String): Flow<Resource<List<VacancyDetails>>>
}
