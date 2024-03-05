package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.Response

data class IndustriesResponse(
    val result: List<IndustriesAllDeal>
): Response()


data class IndustriesAllDeal(
    val id: String,
    val name: String,
    val industries: List<Industries>
)

data class Industries(
    val id: String,
    val name: String
)
