package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.core.data.dto.FilterAreaDto
import ru.practicum.android.diploma.core.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.core.data.dto.SalaryDto

@Suppress("complexity:LongParameterList")
class VacancyDetailsDto(
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
    val url: String?,
    val industry: FilterIndustryDto,
)
