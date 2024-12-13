package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.ItemDto

class HhResponse(
    val arguments: List<HhResponse> = emptyList(),
    val clusters: Any? = null,
    val items: List<ItemDto> = emptyList(),
    val page: Int = 0,
    val perPage: Int = 0,
    val suggests: Any? = null,
    resultCode: Int = 0
) : Response(resultCode)
