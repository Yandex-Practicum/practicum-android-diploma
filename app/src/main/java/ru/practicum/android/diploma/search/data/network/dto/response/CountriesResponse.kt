package ru.practicum.android.diploma.search.data.network.dto.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.Response
import ru.practicum.android.diploma.search.data.network.dto.CountryDto

@Serializable
data class CountriesResponse(

    var results: List<CountryDto>
) : Response()
