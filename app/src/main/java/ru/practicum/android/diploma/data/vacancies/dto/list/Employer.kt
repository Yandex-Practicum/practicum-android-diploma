package ru.practicum.android.diploma.data.vacancies.dto.list

import com.google.gson.annotations.SerializedName

data class Employer(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val vacanciesUrl: String
)
