package ru.practicum.android.diploma.data.dto

data class IndustryDto(
    val id: String,
    val name: String,
    val subIndustries: List<SubIndustryDto> = arrayListOf()
)
