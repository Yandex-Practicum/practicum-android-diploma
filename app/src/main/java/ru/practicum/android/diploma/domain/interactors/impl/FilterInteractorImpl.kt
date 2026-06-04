package ru.practicum.android.diploma.domain.interactors.impl

import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.interactors.FilterInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings

class FilterInteractorImpl(
    private val repository: FilterRepository,
) : FilterInteractor {

    override fun getFilterSettings(): FilterSettings {
        return repository.getFilterSettings()
    }

    override fun saveFilterSettings(settings: FilterSettings) {
        repository.saveFilterSettings(settings)
    }

    override fun clearFilterSettings() {
        repository.clearFilterSettings()
    }
}
