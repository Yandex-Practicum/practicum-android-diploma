package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class EmployerDto(
    @SerializedName("logo_urls")
    val companyLogoUrls: CompanyLogoUrlsDto?,
    @SerializedName("name")
    val companyName: String
)
