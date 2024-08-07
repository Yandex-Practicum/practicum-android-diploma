package ru.practicum.android.diploma.data.dto.components

import com.google.gson.annotations.SerializedName

data class Employer(
    val id: String?,
    val name: String?,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls?,
    val trusted: Boolean?,
    val url: String?,
    @SerializedName("vacancies_url")
    val vacanciesUrl: String?
)
