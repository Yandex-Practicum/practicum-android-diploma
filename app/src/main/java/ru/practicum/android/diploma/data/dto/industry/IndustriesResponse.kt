package ru.practicum.android.diploma.data.dto.industry

import ru.practicum.android.diploma.data.dto.Response

data class IndustriesResponse (
    val industries: List<FilterIndustryDto>
): Response()
