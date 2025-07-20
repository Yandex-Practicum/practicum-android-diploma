package ru.practicum.android.diploma.domain.filters.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.model.Country
import ru.practicum.android.diploma.util.Resource

interface FiltersInteractor {
    fun getCountries(): Flow<Resource<List<Country>>>
}
