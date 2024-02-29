package ru.practicum.android.diploma.data.search.network

import ru.practicum.android.diploma.data.response.IndustryResponse

open class Response {
    var resultCode = 0
    var list: List<IndustryResponse> = emptyList()
}
