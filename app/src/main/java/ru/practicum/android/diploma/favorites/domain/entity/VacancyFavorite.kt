package ru.practicum.android.diploma.favorites.domain.entity

import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.vacancy.data.dto.AdressDto
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.dto.ScheduleDto

data class VacancyFavorite(
    val id: String,
    val name: String,
    val salary: Salary?,
    val employer: EmployerDto?,
    val area: AreaDto?,
    val address: AdressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val description: String?,
    val keySkills: List<KeySkillDto>?,
    val vacancyUrl: String?
)
