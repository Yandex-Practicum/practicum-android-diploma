package ru.practicum.android.diploma.data.dto.field

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlDto?,
    val name: String?,
)
