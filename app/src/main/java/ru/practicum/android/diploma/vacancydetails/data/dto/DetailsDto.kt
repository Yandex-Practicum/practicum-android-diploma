package ru.practicum.android.diploma.vacancydetails.data.dto

data class DetailsDto(
    val id: String,
    val name: String,
    val area: AreaDto,
    val address: AddressDto?,
    val employer: EmploymentDto,
    val salary: SalaryDto,
    val experience: ExperienceDto,
    val contacts: ContactsDto,
    val keySkills: KeySkillsDto,
    val description: String
)

data class AddressDto(
    val city: String,
    val building: String,
    val street: String
)

data class AreaDto(
    val id: Int,
    val name: String,
    val url: String
)

data class ContactsDto(
    val email: String,
    val name: String,
    val phones: List<PhoneDto>?
)

data class PhoneDto(
    val city: String,
    val comment: String?,
    val country: String,
    val number: String
)

data class EmploymentDto(
    val id: Int,
    val name: String
)

data class ExperienceDto(
    val id: Int,
    val name: String
)

data class KeySkillsDto(
    val name: String
)

data class SalaryDto(
    val currency: String?,
    val from: Int?,
    val to: Int?,
    // Признак что границы зарплаты указаны до вычета налогов
    val gross: Boolean?,
)
