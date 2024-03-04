package ru.practicum.android.diploma.data.response

data class AreasResponse(
    val id: Int,
    val parentId: Int?,
    val name: String?,
    val areas: List<AreasResponse>
)
