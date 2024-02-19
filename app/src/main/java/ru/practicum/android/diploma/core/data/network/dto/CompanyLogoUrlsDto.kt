package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class CompanyLogoUrlsDto(
    @SerializedName("90")
    val small: String?,
    @SerializedName("240")
    val medium: String?,
    @SerializedName("original")
    val original: String
)
