package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.Filter

interface FilterSharedPreferencesInteractor {
    fun getFilterSharedPrefs(): Filter?
    fun setFilterSharedPrefs(filter: Filter)
    fun deleteFilterSharedPrefs()
    fun clearRegions(newFilter: Filter)
}
