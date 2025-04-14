package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow

interface IVacancyInteractor {
    suspend fun searchVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>>
    suspend fun getCountries(expression: String): Flow<Pair<List<Area>?, String?>>
    suspend fun getRegion(expression: String): Flow<Pair<List<Area>?, String?>>
    suspend fun getIndustries(expression: String): Flow<Pair<List<Industry>?, String?>>
    suspend fun getVacancyDetails(expression: String): Flow<Pair<List<VacancyDetails>?, String?>>
}
