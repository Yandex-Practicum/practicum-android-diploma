package ru.practicum.android.diploma.filter.domain.model

data class AreaInReference(
    val areas: List<AreaInReference>,
    val id: String,
    val name: String,
    val parentId: String?,
)
