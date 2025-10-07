package ru.practicum.android.diploma.domain.models

data class SearchResultVacancyDetail(
    val id: String?,
    val name: String?,
    val description: String?,
    val salary: Salary?,
    val employer: Employer?,
    val industry: FilterIndustry?,
    val area: Area?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?
)
