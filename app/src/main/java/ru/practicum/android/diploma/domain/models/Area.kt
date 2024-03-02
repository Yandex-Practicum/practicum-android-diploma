package ru.practicum.android.diploma.domain.models

data class Area(
    val areas: List<Area>,
    val id: String,
    val name: String,
    val parentId: String?
)
