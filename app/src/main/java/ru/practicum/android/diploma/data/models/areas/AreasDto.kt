package ru.practicum.android.diploma.data.models.areas

import com.google.gson.annotations.SerializedName

data class AreaWithSubareasDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("areas") val areas: List<AreasDto>
)

data class AreasDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
