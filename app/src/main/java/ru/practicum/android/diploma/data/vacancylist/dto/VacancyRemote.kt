package ru.practicum.android.diploma.data.vacancylist.dto

import com.google.gson.annotations.SerializedName

data class VacanciesRemote (
    val items: List<VacancyRemote>
)

data class VacancyRemote(
    val id: String,
    val name: String,
    val salary: SalaryRemote,
    val address: AddressRemote,
    val employer: EmployerRemote
)

data class SalaryRemote(
    val currency: String,
    val from: Int,
    val to: Int?
)

data class AddressRemote(
    val city: String
)

data class EmployerRemote(
    @SerializedName("logo_urls") val logoUrl: LogoUrlRemote,
    val name: String
)

data class LogoUrlRemote (
    @SerializedName("90") val small: String,
    @SerializedName("240") val medium: String,
    val original: String
)
