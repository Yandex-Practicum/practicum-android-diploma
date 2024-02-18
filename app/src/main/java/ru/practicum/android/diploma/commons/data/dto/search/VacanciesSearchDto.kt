package ru.practicum.android.diploma.commons.data.dto.search

import com.google.gson.annotations.SerializedName

data class VacanciesSearchDto(
    val pages: Long,
    @SerializedName("items") val vacancies: ArrayList<Vacancies>
)

data class Vacancies(
    val id: String,
    val name: String,
    val address: Address?,
    val salary: Salary?,
    @SerializedName("employer") val company: Company
)

data class Address(
    val city: String?
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val to: Int?
)

data class Company(
    val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrls?
)

data class LogoUrls(
    @SerializedName("90") val url90: String,
    @SerializedName("240") val url240: String,
    @SerializedName("original") val urlOriginal: String
)
