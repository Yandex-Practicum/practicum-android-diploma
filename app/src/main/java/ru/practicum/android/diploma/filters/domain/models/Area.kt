package ru.practicum.android.diploma.filters.domain.models

data class Area(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<Area>
)
