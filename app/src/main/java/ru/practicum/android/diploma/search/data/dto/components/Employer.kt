package ru.practicum.android.diploma.search.data.dto.components

import com.google.gson.annotations.SerializedName

data class Employer(
    val id: Int?,
    val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?
)
