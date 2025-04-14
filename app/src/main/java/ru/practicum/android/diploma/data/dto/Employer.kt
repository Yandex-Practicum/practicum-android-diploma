package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Employer(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls? = null,
    val name: String,
)
