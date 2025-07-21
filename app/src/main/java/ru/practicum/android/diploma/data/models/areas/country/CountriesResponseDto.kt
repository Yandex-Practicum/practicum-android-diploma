package ru.practicum.android.diploma.data.models.areas.country

import ru.practicum.android.diploma.data.models.areas.AreasResponseDto
import ru.practicum.android.diploma.data.models.vacancies.Response

data class CountriesResponseDto(
    val countries: List<AreasResponseDto>
) : Response()
