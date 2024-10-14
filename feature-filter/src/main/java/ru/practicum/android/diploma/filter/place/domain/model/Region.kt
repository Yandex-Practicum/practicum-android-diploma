package ru.practicum.android.diploma.filter.place.domain.model

data class Region(
    val id: String,
    val name: String,
    val parentId: String,
    val parentName: String,
)
