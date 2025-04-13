package ru.practicum.android.diploma.data.network.dto

import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Area

data class GetAreasResponse(
    var areas: ArrayList<Area> = arrayListOf(),
) : ApiResponse()
