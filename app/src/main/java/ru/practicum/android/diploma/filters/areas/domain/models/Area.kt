package ru.practicum.android.diploma.filters.areas.domain.models

data class Area(
    val id: String,
    val name: String,
    val parentId: String?,
    val parentName: String? = null,
    val areas: List<Area>
)
