package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

class FiltersInteractorImpl(
    private val filtersRepository: FiltersRepository
) : FiltersInteractor {

    override fun getPrefs(): FiltersSettings = filtersRepository.getPrefs()

    override fun savePrefs(settings: FiltersSettings) {
        filtersRepository.savePrefs(settings)
    }

    override fun clearPrefs() {
        filtersRepository.clearPrefs()
    }
}
