package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    @SerializedName("name")
    val vacancyName: String,
    @SerializedName("alternate_url")
    val alternateUrl: String?,
    val employer: EmployerDto,
    val area: AreaDTO,
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    val salary: SalaryDto?,
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>?,
    val contacts: ContactsDto?,
    val comment: String?,
    val schedule: ScheduleDto?,
    val address: AddressDto?
)

data class EmployerDto(
    val name: String,
    @SerializedName("logo_urls")
    val logo: LogoDto?
)

data class LogoDto(
    @SerializedName("90")
    val small: String?,
    @SerializedName("240")
    val big: String?,
    @SerializedName("original")
    val original: String?
)

data class EmploymentDto(
    val name: String?
)

data class ExperienceDto(
    val name: String?
)

data class SalaryDto(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class KeySkillDto(
    val name: String?
)

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhoneDto?>?
)

data class PhoneDto(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)

data class ScheduleDto(
    val name: String?
)

data class AddressDto(
    @SerializedName("raw")
    val fullAddress: String?
)
