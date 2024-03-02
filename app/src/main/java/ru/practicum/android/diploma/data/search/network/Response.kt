package ru.practicum.android.diploma.data.search.network

import ru.practicum.android.diploma.data.dto.fields.CountryDto
import ru.practicum.android.diploma.domain.models.Industry

open class Response {
    var resultCode = 0
    var countriesList: List<CountryDto> = emptyList()
}
