package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.ItemDto

class HhResponse(
    val arguments: List<HhResponse>,
    val clusters: Any?,
    val items: List<ItemDto>,
    val page: Int,
    val perPage: Int,
    val suggests: Any?,
    resultCode: Int
) : Response(resultCode)
