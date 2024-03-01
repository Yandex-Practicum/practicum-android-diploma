package ru.practicum.android.diploma.data.filters.impl

import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.data.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

class FiltersRepositoryImpl(private val filtersLocalStorage: FiltersLocalStorage) : FiltersRepository {

    override fun getPrefs(): FiltersSettings = filtersLocalStorage.getPrefs()

    override fun savePrefs(settings: FiltersSettings) {
        filtersLocalStorage.savePrefs(settings)
    }

    override fun clearPrefs() {
        filtersLocalStorage.clearPrefs()
    }
}
