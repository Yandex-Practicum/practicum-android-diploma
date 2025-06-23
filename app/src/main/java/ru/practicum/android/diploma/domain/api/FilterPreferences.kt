package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.ui.filter.model.SelectedFilters

interface FilterPreferences {
    fun saveFilters(options: SelectedFilters)
    fun loadFilters(): SelectedFilters?
    fun clearFilters()
}
