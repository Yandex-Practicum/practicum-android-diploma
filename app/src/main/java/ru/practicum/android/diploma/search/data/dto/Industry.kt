package ru.practicum.android.diploma.search.data.dto

data class Industry(
    val id: String,
    val name: String,
    val industries: List<SubIndustry>
)
