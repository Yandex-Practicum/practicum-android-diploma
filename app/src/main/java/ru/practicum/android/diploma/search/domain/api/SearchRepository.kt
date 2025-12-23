package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.model.VacancyFilter

interface SearchRepository {
    fun getAreas(): Flow<Resource<List<FilterAreaDto>>>
    fun getIndustry(): Flow<Resource<List<FilterIndustryDto>>>

    fun getVacancies(
        vacancyFilter: VacancyFilter
    ): Flow<Resource<VacancyResponseDto>>

    fun getVacancyById(
        id: String
    ): Flow<Resource<VacancyDetailDto>>
}
