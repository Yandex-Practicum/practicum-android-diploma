package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.response.CountriesResponse
import ru.practicum.android.diploma.data.dto.response.CountryResponse
import ru.practicum.android.diploma.domain.models.Country

class CountriesConverter {

    fun map(countriesResponse: CountriesResponse): List<Country> {
        return countriesResponse.countries.map {
            convertCountry(it)
        }
    }

    private fun convertCountry(countryResponse: CountryResponse): Country {
        return Country(
            includedRegions = countryResponse.areas.map { convertCountry(it) },
            id = countryResponse.id?.let { countryResponse.id },
            name = countryResponse.name?.let { countryResponse.name },
            parentId = countryResponse.parentId?.let { countryResponse.parentId }
        )
    }
}
