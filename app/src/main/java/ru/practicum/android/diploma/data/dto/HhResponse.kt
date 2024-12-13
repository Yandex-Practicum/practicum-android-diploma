package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.ItemDto
import ru.practicum.android.diploma.data.dto.model.VacancyDto

class HhResponse(
    val arguments: List<HhResponse>,
    val clusters: Any?,
    val fixes: Any?,
    val found: Int,
    val items: List<ItemDto>,
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val suggests: Any?,
    resultCode: Int) : Response(resultCode)
