package ru.practicum.android.diploma.search.data.dto

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val area: FilterAreaDto,
    val skills: List<String>,
    val url: String,
    val industry: FilterIndustryDto
)
