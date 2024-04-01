package ru.practicum.android.diploma.data.filter.industries

import ru.practicum.android.diploma.data.filter.industries.dto.ParentIndustriesResponse
import ru.practicum.android.diploma.data.vacancies.response.Response

data class IndustriesResponse(
    val industries: List<ParentIndustriesResponse>
) : Response()
