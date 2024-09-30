package ru.practicum.android.diploma.search.data.network

data class Area(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<Area> // Список дочерних регионов
)
