package ru.practicum.android.diploma.filter.domain.model

data class Area(
    val id: String,
    val name: String,
    val parentId: String? = null,
    val isSelected: Boolean,
    val areas: List<Area>,
)
