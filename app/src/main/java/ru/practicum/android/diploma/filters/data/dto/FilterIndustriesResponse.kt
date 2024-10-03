package ru.practicum.android.diploma.filters.data.dto

import ru.practicum.android.diploma.util.network.Response

class FilterIndustriesResponse(
    val id: String,
    // тут есть еще список подиндустрий
    val name: String,
) : Response()
