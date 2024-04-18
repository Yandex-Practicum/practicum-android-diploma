package ru.practicum.android.diploma.data.filter.country.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountryList
import ru.practicum.android.diploma.data.filter.country.CountryRequest
import ru.practicum.android.diploma.data.filter.country.response.AreasResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryRepository
import ru.practicum.android.diploma.util.ResourceContentSearch

class CountryRepositoryImpl(
    val networkClient: NetworkClient,
    val customOrder: List<String>
) : CountryRepository {

    override fun searchCountry(): Flow<ResourceContentSearch<List<Country>>> = flow {
        val response = networkClient.doRequestFilter(CountryRequest)

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val countries = (response as AreasResponse).area.mapToCountryList()

                    val sortedCountries = countries.sortedWith(compareBy { country ->
                        !customOrder.contains(country.name)
                    })

                    emit(ResourceContentSearch.SuccessSearch(sortedCountries))
                } catch (e: IOException) {
                    emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
                    throw e
                }
            }

            ResponseCodes.ERROR -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
        }
    }
}
