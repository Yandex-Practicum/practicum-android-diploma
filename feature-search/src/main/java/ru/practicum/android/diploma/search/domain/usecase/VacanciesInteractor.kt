package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.models.sp.FilterSearch

internal interface VacanciesInteractor {
    @Suppress("detekt.LongParameterList")
    fun searchVacancies(
        page: String,
        perPage: String,
        queryText: String,
        industry: String?,
        salary: String?,
        area: String?,
        onlyWithSalary: Boolean = false,
    ): Flow<Pair<PaginationInfo?, String>>

    fun listVacancy(id: String): Flow<Pair<VacancyDetail?, String?>>
    fun listAreas(): Flow<Pair<RegionList?, String?>>
    fun listIndustries(): Flow<Pair<List<IndustryList>?, String?>>

    suspend fun getDataFilter(): FilterSearch
}
