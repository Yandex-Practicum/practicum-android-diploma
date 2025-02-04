package ru.practicum.android.diploma.common.sharedprefs.repository

import ru.practicum.android.diploma.common.sharedprefs.models.Filter

interface SharedPrefsRepository {
    fun updateFilter(updatedFilter: Filter)
    fun clearFilterField(field: String)
    fun clearFilter()
    fun getFilter(): Filter
}
