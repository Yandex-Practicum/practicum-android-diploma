package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.SelectedFilter

interface FilterInteractor {
    suspend fun getSavedFilterSettings(key: String): SelectedFilter
    suspend fun clearFilter(key: String)
    suspend fun saveFilterSettings(key: String, data: SelectedFilter)
}