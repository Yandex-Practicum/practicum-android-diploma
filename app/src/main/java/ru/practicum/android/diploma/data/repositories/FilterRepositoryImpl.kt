package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.storage.filter.FilterSettingsStorage
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.FilterSettings

class FilterRepositoryImpl(
    private val storage: FilterSettingsStorage,
) : FilterRepository {

    override fun getFilterSettings(): FilterSettings {
        return storage.getFilterSettings()
    }

    override fun saveFilterSettings(settings: FilterSettings) {
        storage.saveFilterSettings(settings)
    }

    override fun clearFilterSettings() {
        storage.clearFilterSettings()
    }
}
