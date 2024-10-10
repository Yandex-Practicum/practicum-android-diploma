package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.PaginationInfo
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.VacancyDetail

internal interface VacanciesInteractor {
    fun searchVacancies(page: String, per_page: String, query_text: String): Flow<Pair<PaginationInfo?, String>>
    fun listVacancy(id: String): Flow<Pair<VacancyDetail?, String?>>
    fun listAreas(): Flow<Pair<RegionList?, String?>>
    fun listIndustries(): Flow<Pair<List<IndustryList>?, String?>>
}
