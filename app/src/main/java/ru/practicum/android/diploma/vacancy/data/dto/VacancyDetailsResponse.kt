package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.util.dto.AddressDto
import ru.practicum.android.diploma.util.dto.AreaDto
import ru.practicum.android.diploma.util.dto.EmployerDto
import ru.practicum.android.diploma.util.dto.EmploymentDto
import ru.practicum.android.diploma.util.dto.ExperienceDto
import ru.practicum.android.diploma.util.dto.KeySkillDto
import ru.practicum.android.diploma.util.dto.SalaryDto
import ru.practicum.android.diploma.util.dto.ScheduleDto
import ru.practicum.android.diploma.util.network.Response

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val description: String,
    val keySkills: List<KeySkillDto>,
) : Response()
