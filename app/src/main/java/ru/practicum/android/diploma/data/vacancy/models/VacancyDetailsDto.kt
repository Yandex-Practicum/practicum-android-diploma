package ru.practicum.android.diploma.data.vacancy.models

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response

data class VacancyDetailsDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val experience: ExperienceDto?,
    val employment: EmploymentDto?,
    val schedule: ScheduleDto?,
    val description: String?,
    val keySkills: List<SkillDto>?,
    val address: Address?,
    val area: AreaDto?
) : Response()

data class Address(
    val raw: String?
    // другие поля по необходимости
)

data class AreaDto(
    val name: String?
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class EmployerDto(
    val name: String?,
    val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    @SerializedName("90") val size90: String?
)

data class ExperienceDto(
    val name: String?
)

data class EmploymentDto(
    val name: String?
)

data class ScheduleDto(
    val name: String?
)

data class SkillDto(
    val name: String?
)
