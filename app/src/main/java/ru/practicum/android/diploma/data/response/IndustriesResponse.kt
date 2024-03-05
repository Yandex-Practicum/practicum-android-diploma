package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.Response

//data class IndustriesResponse(
//    val result: List<IndustriesDtoAllDeal>
//) : Response()

data class IndustriesResponse(
    val id: String,
    val name: String,
    val industries: List<IndustriesDto>
) : Response()

data class IndustriesDto(
    val id: String,
    val name: String
)
