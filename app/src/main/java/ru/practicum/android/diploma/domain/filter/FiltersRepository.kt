package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Filters

interface FiltersRepository {
    fun setFilters()
    fun getFiltersFlow(): Flow<Filters>
    fun cancelJob()
}
