package ru.practicum.android.diploma.data.filter.industries.dto

data class ParentIndustriesResponse(
    val id: String,
    val name: String,
    val industries: List<IndustriesDto>
)
