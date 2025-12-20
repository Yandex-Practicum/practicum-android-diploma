package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.network.Resource

interface VacancyRepository {
    fun getAreas(): Flow<Resource<List<FilterAreaDto>>>
}
