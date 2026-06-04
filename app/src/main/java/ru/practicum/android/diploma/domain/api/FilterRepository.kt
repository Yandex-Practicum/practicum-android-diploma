package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterRepository {
    fun getFilterSettings(): FilterSettings
    fun saveFilterSettings(settings: FilterSettings)
    fun clearFilterSettings()
}
