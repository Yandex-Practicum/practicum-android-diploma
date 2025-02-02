package ru.practicum.android.diploma.common.sharedprefs.interactor

import ru.practicum.android.diploma.common.sharedprefs.models.Filter

interface SharedPrefsInteractor {
    fun updateFilter(updatedFilter: Filter)
    fun clearFilter()
    fun getFilter(): Filter
}
