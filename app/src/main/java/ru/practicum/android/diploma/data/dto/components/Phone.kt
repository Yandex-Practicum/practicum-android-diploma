package ru.practicum.android.diploma.data.dto.components

import com.google.gson.annotations.SerializedName

data class Phone(
    @SerializedName("city")
    val cityCode: String?,
    val comment: String?,
    @SerializedName("country")
    val countryCode: String?,
    val formatted: String?,
    val number: String?
)
