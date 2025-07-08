package ru.practicum.android.diploma.data.models.vacancydetails

import com.google.gson.annotations.SerializedName

data class VacancyDetailsResponseDto(
    @SerializedName("name") val name: String,
    @SerializedName("area") val area: AreaDto,
    @SerializedName("salary_range") val salaryRange: SalaryRangeDto?,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("employment_form") val employmentForm: EmploymentFormDto?,
    @SerializedName("description") val description: String,
    @SerializedName("key_skills") val keySkills: List<KeySkills>
)

data class AreaDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

data class SalaryRangeDto(
    @SerializedName("currency") val currency: String,
    @SerializedName("from") val from: Int?,
    @SerializedName("to") val to: Int?
)

data class EmployerDto(
    @SerializedName("name") val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    @SerializedName("90") val logo90: String?
)

data class ExperienceDto(
    @SerializedName("name") val name: String?
)

data class EmploymentFormDto(
    @SerializedName("name") val name: String?
)

data class KeySkills(
    @SerializedName("name") val name: String
)
