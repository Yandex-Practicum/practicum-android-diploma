package ru.practicum.android.diploma.core.data.storage.sharedpreferences

import ru.practicum.android.diploma.filter.domain.models.FilterType

interface FilterStorage {
    fun saveFilters(filters: List<FilterType>)
    fun getFilters(): List<FilterType>
    fun deleteFilter(filterType: FilterType)
    fun clearFilters()
    fun isFilterApplied(): Boolean
    fun setFilterApplied(isApplied: Boolean)
}
