package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.search.domain.Vacancy

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>): Flow<Resource<List<Vacancy>>>
    fun listVacancy(id: String): Flow<Unit>
    fun listAreas(): Flow<Unit>
    fun listIndustries(): Flow<Unit>
}
