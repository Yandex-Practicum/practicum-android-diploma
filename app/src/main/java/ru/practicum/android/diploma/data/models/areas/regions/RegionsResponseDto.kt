package ru.practicum.android.diploma.data.models.areas.regions

import ru.practicum.android.diploma.data.models.areas.AreasResponseDto
import ru.practicum.android.diploma.data.models.vacancies.Response

data class RegionsResponseDto(
    val regions: List<AreasResponseDto>
) : Response()
