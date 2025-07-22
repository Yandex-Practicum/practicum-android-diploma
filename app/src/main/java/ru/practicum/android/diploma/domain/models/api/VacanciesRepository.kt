package ru.practicum.android.diploma.domain.models.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.VacancyFilters
import ru.practicum.android.diploma.domain.models.paging.VacanciesResult
import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails
import ru.practicum.android.diploma.util.Resource

interface VacanciesRepository {
    fun search(vacancy: VacancyFilters): Flow<Resource<VacanciesResult>>
    fun clearLoadedPages()
    fun getVacancyDetailsById(id: String): Flow<Resource<VacancyDetails>>
}
