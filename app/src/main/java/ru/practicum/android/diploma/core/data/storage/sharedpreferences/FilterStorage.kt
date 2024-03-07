package ru.practicum.android.diploma.core.data.storage.sharedpreferences

import ru.practicum.android.diploma.filter.domain.models.FilterType

interface FilterStorage {
    fun saveFilters(filters: List<FilterType>)
    fun getFilters(): List<FilterType>
    fun deleteFilter(filterType: FilterType)
    fun clearFilters()
    fun addCountryFilter(filters: MutableList<FilterType>)
    fun addRegionFilter(filters: MutableList<FilterType>)
    fun addIndustryFilter(filters: MutableList<FilterType>)
    fun addSalaryFilter(filters: MutableList<FilterType>)
    fun addShowWithSalaryFlagFilter(filters: MutableList<FilterType>)
}
