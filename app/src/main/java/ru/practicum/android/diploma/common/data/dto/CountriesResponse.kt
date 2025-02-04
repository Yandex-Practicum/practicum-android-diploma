package ru.practicum.android.diploma.common.data.dto

import ru.practicum.android.diploma.filter.data.dto.model.CountryDto

data class CountriesResponse(
    val result: List<CountryDto>
) : Response()
