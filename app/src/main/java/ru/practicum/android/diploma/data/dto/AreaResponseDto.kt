package ru.practicum.android.diploma.data.dto

data class AreaResponseDto(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<AreaResponseDto>
)
