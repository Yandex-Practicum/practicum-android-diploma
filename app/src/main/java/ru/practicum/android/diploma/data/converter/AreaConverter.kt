package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.filter.country.dto.AreaDtoResponse
import ru.practicum.android.diploma.domain.country.Country

object AreaConverter {

    fun AreaDtoResponse.mapToCountry(): Country = Country(
        id = id,
        parentId = parentId,
        name = name,
        areas = areas
    )

    fun List<AreaDtoResponse>.mapToCountryList(): List<Country> = map { it.mapToCountry() }
}
