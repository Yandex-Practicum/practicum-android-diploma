package ru.practicum.android.diploma.search.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.search.domain.models.RegionList
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyDetail

interface VacanciesInteractor {
    fun searchVacancies(options: Map<String, String>): Flow<Pair<List<Vacancy>?, String?>>
    fun listVacancy(id: String): Flow<Pair<VacancyDetail?, String?>>
    fun listAreas(): Flow<Pair<RegionList?, String?>>
    fun listIndustries(): Flow<Pair<List<IndustryList>?, String?>>
}
