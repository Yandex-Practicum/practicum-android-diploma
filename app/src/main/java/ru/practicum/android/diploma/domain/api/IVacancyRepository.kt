package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse

interface IVacancyRepository {
    fun searchVacancies(req: SearchVacanciesRequest): Flow<Resource<SearchVacanciesResponse>>
    fun getCountries(): Flow<Resource<List<Area>>>
    fun getIndustries(): Flow<Resource<List<Industry>>>
    fun getVacancyDetails(req: GetVacancyDetailsRequest): Flow<Resource<VacancyDetails>>
}
