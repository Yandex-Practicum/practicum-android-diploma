package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class IndustryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("industries")
    val industries: List<IndustryDto>?
)
