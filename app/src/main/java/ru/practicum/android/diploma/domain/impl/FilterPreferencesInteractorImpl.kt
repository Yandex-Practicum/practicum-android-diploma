package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.FilterPreferences
import ru.practicum.android.diploma.domain.api.FilterPreferencesInteractor
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters

class FilterPreferencesInteractorImpl(
    private val filterPreferences: FilterPreferences,
) : FilterPreferencesInteractor {
    override fun saveFilters(options: SelectedFilters) {
        filterPreferences.saveFilters(options)
    }

    override fun loadFilters(): SelectedFilters? {
        return filterPreferences.loadFilters()
    }

    override fun clearFilters() {
        filterPreferences.clearFilters()
    }

}
