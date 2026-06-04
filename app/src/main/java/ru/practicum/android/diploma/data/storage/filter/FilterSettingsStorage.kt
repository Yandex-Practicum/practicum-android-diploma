package ru.practicum.android.diploma.data.storage.filter

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsStorage {
    fun getFilterSettings(): FilterSettings
    fun saveFilterSettings(settings: FilterSettings)
    fun clearFilterSettings()
}
