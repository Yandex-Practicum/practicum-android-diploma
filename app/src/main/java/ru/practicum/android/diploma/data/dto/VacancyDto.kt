package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyCardDto(
    @SerializedName("id")
    val vacancyId: String,
    @SerializedName("name")
    val vacancyName: String,
    @SerializedName("company")
    val companyName: String?,
    @SerializedName("city")
    val areaName: String?,
    @SerializedName("salary")
    val salary: SalaryDto?,
    @SerializedName("logo")
    val logoUrl: String?
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
    @SerializedName("url")
    val alternateUrl: String?,
    val industry: FilterIndustryDto
)

data class EmployerDto(
    val id: String?,
    val name: String?,
    @SerializedName("logo")
    val logoUrl: String?
)

data class AreaDto(
    val id: String?,
    val name: String?
)

data class KeySkillDto(
    val name: String?
)

data class ExperienceDto(
    val id: String?,
    val name: String?
)

data class ScheduleDto(
    val id: String?,
    val name: String?
)

data class EmploymentDto(
    val id: String?,
    val name: String?
)

data class AddressDto(
    val id: String?,
    val city: String?,
    val street: String?,
    val building: String?,
    val raw: String?
)

data class ContactsDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val phones: List<PhoneDto>?
)

data class PhoneDto(
    val formatted: String?,
    val comment: String?
)
