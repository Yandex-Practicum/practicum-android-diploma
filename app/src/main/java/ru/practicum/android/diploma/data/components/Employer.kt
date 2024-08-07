package ru.practicum.android.diploma.data.components

import com.google.gson.annotations.SerializedName

data class Employer(
    val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?
)
