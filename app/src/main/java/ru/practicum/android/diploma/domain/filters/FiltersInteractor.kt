package ru.practicum.android.diploma.domain.filters

import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

interface FiltersInteractor {
    fun getPrefs(): FiltersSettings
    fun savePrefs(settings: FiltersSettings)
    fun clearPrefs()
}
