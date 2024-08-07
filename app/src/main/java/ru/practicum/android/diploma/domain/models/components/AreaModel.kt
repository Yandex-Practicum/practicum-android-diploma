package ru.practicum.android.diploma.domain.models.components

data class AreaModel(
    val id: String?,
    val name: String?,
    val countryId: String?,
    val areas: List<AreaModel>?
)
