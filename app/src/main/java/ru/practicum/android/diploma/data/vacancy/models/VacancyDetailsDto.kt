package ru.practicum.android.diploma.data.vacancy.models

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response

data class VacancyDetailsDto(
    val id: String,
    val name: String,
    @SerializedName("salary_range") val salary: SalaryDto?,
    val employer: EmployerDto?,
    val employment: EmploymentDto?,
    val experience: InfoIdName,
    @SerializedName("employment_form") val employmentForm: InfoIdName?,
    @SerializedName("work_schedule_by_days") val schedule: List<InfoIdName>?,
    @SerializedName("professional_roles") val professionalRoles: List<InfoIdName>,
    val description: String,
    @SerializedName("key_skills") val keySkills: List<InfoName>,
    val area: InfoIdName,
    val address: AdressInfo?,
    @SerializedName("alternate_url") val alternateUrl: String?,
) : Response()

data class AdressInfo(
    val city: String?
)

data class EmploymentDto(
    val name: String?
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String
)

data class EmployerDto(
    val name: String?,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    @SerializedName("90") val size90: String?
)

data class InfoIdName(
    val id: String,
    val name: String
)

data class InfoName(
    val name: String
)
