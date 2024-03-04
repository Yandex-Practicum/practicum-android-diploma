package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.dto.fields.CountryDto
import ru.practicum.android.diploma.data.search.network.Response

data class CountryResponse(val results: List<CountryDto>) : Response()

