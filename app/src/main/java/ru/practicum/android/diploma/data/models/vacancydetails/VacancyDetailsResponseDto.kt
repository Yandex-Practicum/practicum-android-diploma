package ru.practicum.android.diploma.data.models.vacancydetails

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.models.vacancies.Response
import ru.practicum.android.diploma.data.models.vacancies.SalaryRangeDto

data class VacancyDetailsResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("area") val area: AreaDto,
    @SerializedName("salary_range") val salaryRange: SalaryRangeDto?,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("employment_form") val employmentForm: EmploymentFormDto?,
    @SerializedName("description") val description: String,
    @SerializedName("key_skills") val keySkills: List<KeySkills>?,
    @SerializedName("work_format") val workFormat: List<WorkFormatDto>?,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("address") val address: Address?,
) : Response()

data class AreaDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

data class Address(
    @SerializedName("city") val city: String?
)

data class EmployerDto(
    @SerializedName("name") val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?,
)

data class LogoUrlsDto(
    @SerializedName("90") val logo90: String?
)

data class ExperienceDto(
    @SerializedName("name") val name: String?
)

data class EmploymentFormDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?
)

data class WorkFormatDto(
    @SerializedName("name") val name: String
)

data class KeySkills(
    @SerializedName("name") val name: String
)
