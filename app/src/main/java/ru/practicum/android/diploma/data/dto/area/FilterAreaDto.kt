package ru.practicum.android.diploma.data.dto.area

import ru.practicum.android.diploma.data.dto.Response

data class FilterAreaDto (
    val id: Integer,
    val name: String,
    val parentId: Integer,
    val areas: Array<FilterAreaDto>
): Response()
