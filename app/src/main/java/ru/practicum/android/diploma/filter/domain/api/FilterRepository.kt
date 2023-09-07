package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.ui.models.SelectedData

interface FilterRepository {

    suspend fun saveSelectedData(key: String, selectedData: SelectedData)
    suspend fun getSelectedData(key: String): SelectedData
}