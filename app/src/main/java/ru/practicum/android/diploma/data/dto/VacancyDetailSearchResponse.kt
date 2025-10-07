package ru.practicum.android.diploma.data.dto

data class VacancyDetailSearchResponse(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto,
    val employer: EmployerDto,
    val industry: FilterIndustryDto,
    val area: AreaDto,
    val experience: ExperienceDto,
    val schedule: ScheduleDto,
    val employment: EmploymentDto
)
