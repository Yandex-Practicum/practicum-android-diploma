package ru.practicum.android.diploma.domain.models

data class VacancyDetail(
    val id: String,
    val name: String,
    val description: String,
    val salary: VacancySalary?,
    val address: VacancyAddress?,
    val experience: VacancyExperience?,
    val schedule: VacancySchedule?,
    val employment: VacancyEmployment?,
    val contacts: VacancyContacts?,
    val employer: VacancyEmployer?,
    val areaName: String?,
    val skills: List<String>,
    val url: String,
)

data class VacancyAddress(
    val city: String?,
    val street: String?,
    val building: String?,
    val raw: String?,
)

data class VacancyExperience(
    val id: String?,
    val name: String?,
)

data class VacancySchedule(
    val id: String?,
    val name: String?,
)

data class VacancyEmployment(
    val id: String?,
    val name: String?,
)

data class VacancyContacts(
    val name: String?,
    val email: String?,
    val phones: List<VacancyPhone>,
)

data class VacancyPhone(
    val comment: String?,
    val formatted: String,
)

data class VacancyEmployer(
    val name: String,
    val logo: String?,
)
