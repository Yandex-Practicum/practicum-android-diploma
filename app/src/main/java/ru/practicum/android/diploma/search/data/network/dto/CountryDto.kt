package ru.practicum.android.diploma.search.data.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(

    val id: String? = "",
    var name: String? = "",
    @SerializedName("areas")var areas: List<RegionArea?>
)