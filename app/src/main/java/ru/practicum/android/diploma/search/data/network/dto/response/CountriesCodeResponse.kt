package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.dto.CountryDto

@Serializable
data class CountriesCodeResponse(
    var results: List<CountryDto>
) : CodeResponse()
