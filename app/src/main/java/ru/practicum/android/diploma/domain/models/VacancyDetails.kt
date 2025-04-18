package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val area: Area? = Area(),
    val description: String? = null,
    val employer: Employer? = null,
    val keySkills: List<KeySkill> = arrayListOf(),
    val salary: Salary? = null,
    val salaryRange: Salary? = null,
    val experience: Experience? = null
)
