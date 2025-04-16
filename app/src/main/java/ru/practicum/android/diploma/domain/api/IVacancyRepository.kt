package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.ReceivedVacanciesData
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface IVacancyRepository {
    fun searchVacancies(expression: String): Flow<Resource<ReceivedVacanciesData>>
    fun getCountries(): Flow<Resource<List<Area>>>
    fun getIndustries(): Flow<Resource<List<Industry>>>
    fun getVacancyDetails(vacancyId: String): Flow<Resource<VacancyDetails>>
}
