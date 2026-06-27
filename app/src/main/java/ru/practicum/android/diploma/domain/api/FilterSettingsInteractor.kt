package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsInteractor {
    fun getFilterSettings(): FilterSettings
    fun saveFilterSettings(settings: FilterSettings)
    fun clearFilterSettings()
}
