package ru.practicum.android.diploma.data.models.industries.remote

import ru.practicum.android.diploma.data.models.industries.IndustryGroupDto
import ru.practicum.android.diploma.data.models.vacancies.Response

class IndustryResponseDto(
    val industries: List<IndustryGroupDto>
) : Response()
