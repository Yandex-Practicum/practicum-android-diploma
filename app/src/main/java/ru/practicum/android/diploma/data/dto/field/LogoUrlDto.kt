package ru.practicum.android.diploma.data.dto.field

import com.google.gson.annotations.SerializedName

data class LogoUrlDto(
    @SerializedName("90")
    val logoUrl90: String,
    @SerializedName("240")
    val logoUrl240: String,
    val logoUrlOrigin: String
)
