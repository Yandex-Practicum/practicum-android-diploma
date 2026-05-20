package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto (
    private val name: String,
    @SerializedName("logo_urls")
    private val logoUrls: LogoUrlsDto?
)
