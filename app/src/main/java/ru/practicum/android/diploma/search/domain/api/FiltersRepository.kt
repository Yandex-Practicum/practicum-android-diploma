package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.domain.model.Resource

interface FiltersRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
}
