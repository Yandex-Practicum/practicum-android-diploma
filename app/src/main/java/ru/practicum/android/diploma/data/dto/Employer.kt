package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Employer(
    var id: String,
    @SerializedName("logo_urls")
    var logoUrls: LogoUrls? = null,
    var name: String,
)
