package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.Response

data class IndustriesResponse(
    val industries: List<ParentIndustries>
) : Response()

data class ParentIndustries(
    val id: String,
    val name: String,
    val industries: List<IndustriesDto>
)

data class IndustriesDto(
    val id: String,
    val name: String
)
