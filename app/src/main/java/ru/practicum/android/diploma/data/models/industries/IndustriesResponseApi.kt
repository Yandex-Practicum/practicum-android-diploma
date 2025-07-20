package ru.practicum.android.diploma.data.models.industries

import com.google.gson.annotations.SerializedName

data class IndustryDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)

data class IndustryGroupDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("industries") val industries: List<IndustryDto>
)
