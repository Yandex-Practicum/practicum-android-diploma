package ru.practicum.android.diploma.data.dto.responseUnits

import com.google.gson.annotations.SerializedName

data class Employer(
    val id: String,
    @SerializedName("logo-urls")
    val logoUrls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val vacancies_url: String
)
