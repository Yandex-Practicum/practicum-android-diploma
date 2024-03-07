package ru.practicum.android.diploma.filter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.dto.CountryResponse
import ru.practicum.android.diploma.core.data.storage.sharedpreferences.FilterStorage
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.util.Resource

class FilterRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filterStorage: FilterStorage
) : FilterRepository {
    override fun getCountries(): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.getCountries()
        when (response.resultCode) {
            NetworkClient.SUCCESSFUL_CODE -> {
                val countryResponse = response as CountryResponse
                val domainModel = countryResponse.countries.map { countryDto -> VacancyMapper.mapToDomain(countryDto) }
                emit(Resource.Success(data = domainModel))
            }

            NetworkClient.NETWORK_ERROR_CODE -> {
                emit(Resource.InternetError())
            }

            NetworkClient.EXCEPTION_ERROR_CODE -> {
                emit(Resource.ServerError())
            }
        }
    }

    override fun saveFilterToLocalStorage(filterType: FilterType) {
        when (filterType) {
            is FilterType.Country -> {
                filterStorage.saveFilters(listOf(FilterType.Country(id = filterType.id, name = filterType.name)))
            }

            is FilterType.Region -> {
                filterStorage.saveFilters(listOf(FilterType.Region(filterType.id, filterType.name)))
            }

            is FilterType.Industry -> {
                filterStorage.saveFilters(listOf(FilterType.Industry(filterType.id, filterType.name)))
            }

            is FilterType.Salary -> {
                filterStorage.saveFilters(listOf(FilterType.Salary(filterType.amount)))
            }

            is FilterType.ShowWithSalaryFlag -> {
                filterStorage.saveFilters(listOf(FilterType.ShowWithSalaryFlag(filterType.flag)))
            }
        }
    }

    override fun getCountryFilterFromLocalStorage(): Country? {
        val country = filterStorage.getFilters().find { it is FilterType.Country }
        if (country != null && country is FilterType.Country) {
            return Country(id = country.id, name = country.name)
        }
        return null
    }

    override fun getAreaFilterFromLocalStorage(): Area? {
        val area = filterStorage.getFilters().find { it is FilterType.Region }
        if (area != null && area is FilterType.Region) {
            return Area(id = area.id, name = area.name, null)
        }
        return null
    }

    override fun getFilters(): List<FilterType> {
        return filterStorage.getFilters()
    }

    override fun deleteFilters() {
        filterStorage.clearFilters()
    }

    override fun setFilterApplied(isApplied: Boolean) {
        filterStorage.setFilterApplied(isApplied)
    }

    override fun isFilterApplied(): Boolean {
        return filterStorage.isFilterApplied()
    }
}
