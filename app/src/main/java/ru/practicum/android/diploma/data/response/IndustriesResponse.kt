package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.Response

data class IndustriesResponse(
    val id: String,
    val name: String,
    val industries: List<Industries>
) : Response()

data class Industries(
    val id: String,
    val name: String
)
