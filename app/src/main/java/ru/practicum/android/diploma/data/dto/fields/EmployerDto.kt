package ru.practicum.android.diploma.data.dto.fields

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val name: String,
    @SerializedName("logo_urls") val logoUrlsDto: LogoUrlsDto?,
    @SerializedName("alternate_url") val alternateUrl: String?
)

