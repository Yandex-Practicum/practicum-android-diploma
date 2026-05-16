package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.data.dto.area.FilterAreaDto
import ru.practicum.android.diploma.data.dto.industry.FilterIndustryDto

data class VacancyDetailDto (
    val id: String,
    val name: String,
    val description: String,// (HTML),
    val salary: Salary?,
    val address: Address?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?,
    val contacts: Contacts?,
    val employer: Employer,
    val area: FilterAreaDto,
    val skills: Array<String>,
    val url: String,
    val industry: FilterIndustryDto
)

data class Salary (
    val from: Integer?,
    val to: Integer?,
    val currency: String?
)

data class Address (
    val id: String,
    val city: String,
    val street: String,
    val building: String,
    val raw: String
)

data class Experience (
    val id: String,
    val name: String
)

data class Schedule (
    val id: String,
    val name: String
)

data class Employment (
    val id: String,
    val name: String
)
data class Contacts (
    val id: String,
    val name: String,
    val email: String,
    val phones: Array<Phone>
)

data class Phone (
    val comment: String?,
    val formatted: String
)

data class Employer(
    val id: String,
    val name: String,
    val logo: String
)
