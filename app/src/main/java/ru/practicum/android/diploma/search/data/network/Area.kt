package ru.practicum.android.diploma.search.data.network

data class Area(
    val id: String,
    val parent_id: String?,
    val name: String,
    val areas: List<Area> // Список дочерних регионов
)
