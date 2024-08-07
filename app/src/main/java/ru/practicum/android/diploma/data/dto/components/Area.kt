package ru.practicum.android.diploma.data.dto.components

data class Area(
    val id: String?,
    val name: String?,
    val countryId: String?,
    val areas: List<Area>?
)
