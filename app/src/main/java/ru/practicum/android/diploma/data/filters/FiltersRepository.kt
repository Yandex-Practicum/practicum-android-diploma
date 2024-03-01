package ru.practicum.android.diploma.data.filters

import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

interface FiltersRepository {
    fun getPrefs(): FiltersSettings
    fun savePrefs(settings: FiltersSettings)
    fun clearPrefs()
}
