package ru.practicum.android.diploma.domain.filters.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings
import ru.practicum.android.diploma.domain.models.Country

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

    override suspend fun getCountries(): Flow<Resource<List<Country>>> {
        return filtersRepository.getCountries()
    }
}
