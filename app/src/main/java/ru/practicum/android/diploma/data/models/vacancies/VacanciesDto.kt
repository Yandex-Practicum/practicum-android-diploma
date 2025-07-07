package ru.practicum.android.diploma.data.models.vacancies

import com.google.gson.annotations.SerializedName

data class VacanciesDto(
    @SerializedName("name") val name: String,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("id") val id: String,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("salary_range") val salaryRange: SalaryRangeDto?
)

data class EmployerDto(
    @SerializedName("name") val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    @SerializedName("90") val logo90: String?
)

data class SalaryRangeDto(
    @SerializedName("currency") val currency: String,
    @SerializedName("from") val from: Int?,
    @SerializedName("to") val to: Int?
)
