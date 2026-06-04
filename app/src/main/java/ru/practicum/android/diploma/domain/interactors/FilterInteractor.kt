package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterInteractor {
    fun getFilterSettings(): FilterSettings
    fun saveFilterSettings(settings: FilterSettings)
    fun clearFilterSettings()
}
