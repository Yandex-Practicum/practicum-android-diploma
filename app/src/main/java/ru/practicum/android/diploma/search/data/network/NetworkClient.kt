package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.domain.model.VacancyFilter

interface NetworkClient {

    suspend fun getAreas(): Resource<List<FilterAreaDto>>

    suspend fun getIndustry(): Resource<List<FilterIndustryDto>>

    suspend fun getVacancies(
        vacancyFilter: VacancyFilter
    ): Resource<VacancyResponseDto>

    suspend fun getVacancyById(
        id: String
    ): Resource<VacancyDetailDto>
}
