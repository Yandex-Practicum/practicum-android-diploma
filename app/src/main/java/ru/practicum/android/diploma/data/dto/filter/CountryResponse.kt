package ru.practicum.android.diploma.data.dto.filter

import ru.practicum.android.diploma.data.dto.Response

data class CountryResponse(
    val countries: List<CountryDto>
) : Response()
