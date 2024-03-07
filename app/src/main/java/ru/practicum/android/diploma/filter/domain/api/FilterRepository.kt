package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.util.Resource

interface FilterRepository {
    fun getCountries(): Flow<Resource<List<Country>>>
    fun saveFilterToLocalStorage(filterType: FilterType)
    fun getCountryFilterFromLocalStorage(): Country?
    fun getAreaFilterFromLocalStorage(): Area?
    fun getFilters(): List<FilterType>
    fun deleteFilters()
    fun setFilterApplied(isApplied: Boolean)
    fun isFilterApplied(): Boolean
}
