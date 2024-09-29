package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyDetail

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>): Flow<Resource<List<Vacancy>>>
    fun listVacancy(id: String): Flow<Resource<VacancyDetail>>
    fun listAreas(): Flow<Unit>
    fun listIndustries(): Flow<Unit>
}
