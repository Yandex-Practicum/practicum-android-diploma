package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.util.Resource

interface FilterRepository {
    fun getCountries(): Flow<Resource<List<Country>>>
}
