package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
