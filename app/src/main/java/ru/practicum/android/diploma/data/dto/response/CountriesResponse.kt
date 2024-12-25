package ru.practicum.android.diploma.data.dto.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

data class CountriesResponse(
    val countries: List<CountryResponse>
) : Response()

data class CountryResponse(
    val areas: List<CountryResponse>,
    val id: String? = null,
    val name: String? = null,
    @SerializedName("parent_id") val parentId: String? = null
)
