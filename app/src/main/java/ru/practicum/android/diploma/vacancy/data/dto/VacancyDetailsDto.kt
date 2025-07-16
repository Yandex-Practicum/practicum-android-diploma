package ru.practicum.android.diploma.vacancy.data.dto

import com.google.gson.annotations.SerializedName

// DTO классы для ответа от /vacancies/{vacancy_id}
// Структура примерная, основана на документации HH API

data class VacancyDetailsDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val description: String, // HTML
    val experience: ExperienceDto?,
    val employment: EmploymentDto?,
    val schedule: ScheduleDto?,
    val employer: EmployerDto?,
    val area: AreaDto,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>,
    @SerializedName("alternate_url") val alternateUrl: String,
)

data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class ExperienceDto(
    val name: String
)

data class EmploymentDto(
    val name: String
)

data class ScheduleDto(
    val name: String
)

data class EmployerDto(
    val name: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    val original: String?
)

data class AreaDto(
    val name: String
)

data class KeySkillDto(
    val name: String
)
