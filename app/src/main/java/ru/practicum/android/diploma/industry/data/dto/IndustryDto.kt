package ru.practicum.android.diploma.industry.data.dto

data class IndustryDto(
    val id: String,
    val name: String
)

typealias IndustriesDto = List<IndustryDto>
