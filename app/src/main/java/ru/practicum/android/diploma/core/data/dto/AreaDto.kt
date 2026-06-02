package ru.practicum.android.diploma.core.data.dto

data class AreaDto(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<AreaDto>,
)

typealias AreasDto = List<AreaDto>
