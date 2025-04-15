package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails

interface IVacancyInteractor {
    fun searchVacancies(expression: String): Flow<Pair<List<Vacancy>?, String?>>
    fun getCountries(): Flow<Pair<List<Area>?, String?>>
    fun getRegion(): Flow<Pair<List<Area>?, String?>>
    fun getIndustries(): Flow<Pair<List<Industry>?, String?>>
    fun getVacancyDetails(vacancyId: String): Flow<Pair<VacancyDetails?, String?>>
}
