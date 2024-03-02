package ru.practicum.android.diploma.data.filters.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.Constant
import ru.practicum.android.diploma.data.Convertors
import ru.practicum.android.diploma.data.filters.FiltersRepository
import ru.practicum.android.diploma.data.response.CountryResponse
import ru.practicum.android.diploma.data.search.network.CountriesRequest
import ru.practicum.android.diploma.data.search.network.NetworkClient
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.data.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings
import ru.practicum.android.diploma.domain.models.Country

class FiltersRepositoryImpl(
    private val filtersLocalStorage: FiltersLocalStorage,
    private val networkClient: NetworkClient
) : FiltersRepository {

    override fun getPrefs(): FiltersSettings = filtersLocalStorage.getPrefs()

    override fun savePrefs(settings: FiltersSettings) {
        filtersLocalStorage.savePrefs(settings)
    }
    override fun clearPrefs() {
        filtersLocalStorage.clearPrefs()
    }

    override suspend fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(CountriesRequest)
        when (response.resultCode) {
            Constant.NO_CONNECTIVITY_MESSAGE -> {
                emit(Resource(data = null, code = Constant.NO_CONNECTIVITY_MESSAGE))
            }

            Constant.SUCCESS_RESULT_CODE -> {
                emit(Resource(data = response.countriesList.map { countryDto ->  Convertors().convertorToCountry(countryDto) }, code = Constant.SUCCESS_RESULT_CODE))
            }

            else -> {
                emit(Resource(data = null, code = Constant.SERVER_ERROR))
            }
        }
    }
}
