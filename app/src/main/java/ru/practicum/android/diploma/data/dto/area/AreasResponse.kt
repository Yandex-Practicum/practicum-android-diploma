package ru.practicum.android.diploma.data.dto.area

import ru.practicum.android.diploma.data.dto.Response

data class AreasResponse(
    val areas: List<FilterAreaDto>
): Response()
