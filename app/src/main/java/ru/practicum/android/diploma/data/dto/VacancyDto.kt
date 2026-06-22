package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyCardDto(
    val vacancyId: String,
    val vacancyName: String,
    val companyName: String?,
    val areaName: String?,
    val salary: SalaryDto?,
    val shareUrl: String?
)

data class VacancyDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto?,
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val address: AddressDto?,
    val contacts: ContactsDto?,
    @SerializedName("alternate_url")
    val alternateUrl: String?
)

data class EmployerDto(
    val id: String?,
    val name: String?,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlsDto?
)

data class LogoUrlsDto(
    val original: String?
)

data class AreaDto(
    val id: String?,
    val name: String?
)

data class KeySkillDto(
    val name: String?
)

data class ExperienceDto(
    val name: String?
)

data class ScheduleDto(
    val name: String?
)

data class EmploymentDto(
    val name: String?
)

data class AddressDto(
    val formatted: String?
)

data class ContactsDto(
    val name: String?,
    val email: String?,
    val phones: List<PhoneDto>?
)

data class PhoneDto(
    val formatted: String?,
    val comment: String?
)
