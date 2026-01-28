package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: Salary?,
    val address: Address?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?,
    val contacts: Contacts?,
    val employer: Employer,
    val area: FilterArea,
    val skills: List<String>?,
    val url: String,
    val industry: IndustryDto
)

data class Salary(val from: Int?, val to: Int?, val currency: String?)
data class Address(val city: String, val street: String, val building: String, val fullAddress: String)
data class Experience(val id: String, val name: String)
data class Schedule(val id: String, val name: String)
data class Employment(val id: String, val name: String)
data class Contacts(val id: String, val name: String, val email: String, val phones: List<PhoneDto>?)
data class Employer(val id: String, val name: String, val logo: String?)
data class FilterArea(val id: Int, val name: String, val parentId: Int?, val areas: List<FilterArea>?)
