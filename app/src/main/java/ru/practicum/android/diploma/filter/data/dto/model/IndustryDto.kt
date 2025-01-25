package ru.practicum.android.diploma.filter.data.dto.model

data class IndustryDto(
    val id: String,
    val name: String,
    val industries: List<IndustryDto>? = null,
)
