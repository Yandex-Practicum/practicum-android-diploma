package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class IndustryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)
