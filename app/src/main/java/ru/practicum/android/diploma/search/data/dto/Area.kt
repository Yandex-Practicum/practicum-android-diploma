package ru.practicum.android.diploma.search.data.dto

data class Area(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<Area> // Список дочерних регионов
)
