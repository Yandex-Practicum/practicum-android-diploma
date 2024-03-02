package ru.practicum.android.diploma.data.dto.fields

import ru.practicum.android.diploma.data.search.network.Response

data class CountryDto(
    val id: String,
    val name: String?,
    val url: String?,
) : Response()
