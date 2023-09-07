package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.ui.models.SelectedFilter

interface FilterRepository {

    suspend fun saveSavedFilterSettings(key: String, selectedFilter: SelectedFilter)
    suspend fun getSaveFilterSettings(key: String): SelectedFilter
}