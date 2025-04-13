package ru.practicum.android.diploma.data.network.dto

import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Industry

data class GetIndustriesResponse(
    var industries: ArrayList<Industry> = arrayListOf()
) : ApiResponse()
