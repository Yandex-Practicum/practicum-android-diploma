package ru.practicum.android.diploma.filter.domain.model

data class City(
    val id: String,
    val name: String,
    val parentId: String? = null,
    val isSelected: Boolean,
)
