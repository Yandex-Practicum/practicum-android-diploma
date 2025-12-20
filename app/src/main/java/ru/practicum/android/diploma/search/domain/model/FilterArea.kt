package ru.practicum.android.diploma.search.domain.model

data class FilterArea(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterArea>
)
