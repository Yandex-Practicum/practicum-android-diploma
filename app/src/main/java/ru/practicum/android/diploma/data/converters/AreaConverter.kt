package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.AreaDto
import ru.practicum.android.diploma.domain.country.Country

object AreaConverter {
    fun AreaDto.mapToCountry(): Country {
        return Country(
            id = id,
            parentId = parentId,
            name = name,
            areas = areas
        )
    }

    fun List<AreaDto>.mapToCountryList(): List<Country> = map { it.mapToCountry() }
}
