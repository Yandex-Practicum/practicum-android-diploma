package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.domain.models.Filter

interface FilterSharedPreferencesRepository {
    fun getFilterSharedPrefs(): Filter?
    fun setFilterSharedPrefs(newFilter: Filter)
    fun clearFilterSharedPrefs()
    fun clearRegions(newFilter: Filter)
}
