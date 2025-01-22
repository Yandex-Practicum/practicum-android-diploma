package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val vacancyId: Long,
    val name: String?,
    val area: Area?,
    val employer: Employer?,
    val salary: Salary?,
    val experience: Experience?,
    val employmentForm: EmploymentForm?,
    val schedule: Schedule?
)
