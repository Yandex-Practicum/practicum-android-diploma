package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings
import ru.practicum.android.diploma.domain.models.Country

interface FiltersInteractor {
    fun getPrefs(): FiltersSettings
    fun savePrefs(settings: FiltersSettings)
    fun clearPrefs()
    suspend fun getCountries(): Flow<Resource<List<Country>>>

}
