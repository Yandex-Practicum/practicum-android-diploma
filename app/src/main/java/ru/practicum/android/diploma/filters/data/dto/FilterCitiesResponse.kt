package ru.practicum.android.diploma.filters.data.dto

import ru.practicum.android.diploma.util.network.Response

class FilterCitiesResponse(
    val areas: List<FilterCitiesDto>,
    val id: String,
    val name: String,
) : Response()
