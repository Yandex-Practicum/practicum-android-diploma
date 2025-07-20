package ru.practicum.android.diploma.domain.models.filters

data class Country(
    val id: String,
    val name: String,
    val parentId: String?,
)
