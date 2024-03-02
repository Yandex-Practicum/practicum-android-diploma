package ru.practicum.android.diploma.data.storage

import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

interface FiltersStorage {

    fun getPrefs(): FiltersSettings
    fun savePrefs(settings: FiltersSettings)
    fun clearPrefs()

}
