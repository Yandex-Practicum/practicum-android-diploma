package ru.practicum.android.diploma.data.models.areas

import com.google.gson.annotations.SerializedName

data class AreasResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("parent_id") val parentId: String?,
    @SerializedName("areas") val areas: List<AreasResponseDto> = emptyList(),
    val countryName: String? = null
)

data class AreaWithSubareasDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("areas") val areas: List<AreasResponseDto>
)
