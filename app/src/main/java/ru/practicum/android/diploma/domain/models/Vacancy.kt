package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val vacancyName: String,
    val companyName: String,
    val alternateUrl: String?,
    val logoUrl: String?,
    val area: String?,
    val employment: String?,
    val experience: String?,
    val salary: Salary?,
    val description: String?,
    val keySkills: List<String?>,
    val contacts: Contacts?,
    val comment: String?,
    val schedule: String?,
    val address: String?
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: List<Phone?>?
)

data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)
