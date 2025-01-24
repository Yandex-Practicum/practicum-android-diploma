package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.AddressDto

data class Vacancy(
    val vacancyId: Long,
    val name: String?,
    val area: Area?,
    val employer: Employer?,
    val salary: Salary?,
    val experience: Experience?,
    val employmentForm: EmploymentForm?,
    val employment: EmploymentForm?,
    val schedule: Schedule?,
    val description: String?,
    val keySkills: List<Skill?>,
    val alternateUrl: String?,
    val address: Address?
)
