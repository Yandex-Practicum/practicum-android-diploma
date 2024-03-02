package ru.practicum.android.diploma.data.search.network

import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.domain.models.Industry

open class Response {
    var resultCode = 0
    var list: List<Industry> = emptyList()
}
