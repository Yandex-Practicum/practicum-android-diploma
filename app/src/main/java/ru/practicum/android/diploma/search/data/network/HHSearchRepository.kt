package ru.practicum.android.diploma.search.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.data.dto.AreasDto
import ru.practicum.android.diploma.common.data.dto.IndustriesDto
import ru.practicum.android.diploma.common.data.dto.Resource
import ru.practicum.android.diploma.common.data.dto.VacancyItemDto

interface HHSearchRepository {
    fun getVacancies(query: String, page: Int, perPage: Int): Flow<Resource<VacancyItemDto>>
    fun getVacancy(id: String): Flow<Resource<VacancyItemDto>>
    fun getAreas(): Flow<Resource<List<AreasDto>>>
    fun getIndustries(): Flow<Resource<List<IndustriesDto>?>>
}
