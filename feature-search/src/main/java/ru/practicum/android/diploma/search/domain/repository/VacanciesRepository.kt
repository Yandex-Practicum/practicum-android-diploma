package ru.practicum.android.diploma.search.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.VacancyDetail

internal interface VacanciesRepository {
    @Suppress("detekt.LongParameterList")
    fun searchVacancies(
        page: String,
        perPage: String,
        queryText: String,
        industry: String?,
        salary: String?,
        area: String?,
        onlyWithSalary: Boolean = false,
    ): Flow<Resource<PaginationInfo>>

    fun listVacancy(id: String): Flow<Resource<VacancyDetail>>
    fun listAreas(): Flow<Resource<RegionList>>
    fun listIndustries(): Flow<Resource<List<IndustryList>>>
}
