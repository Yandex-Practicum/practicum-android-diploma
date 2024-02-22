package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.field.AreaDto
import ru.practicum.android.diploma.data.dto.field.ContactsDto
import ru.practicum.android.diploma.data.dto.field.EmployerDto
import ru.practicum.android.diploma.data.dto.field.EmploymentDto
import ru.practicum.android.diploma.data.dto.field.ExperienceDto
import ru.practicum.android.diploma.data.dto.field.KeySkillsDto
import ru.practicum.android.diploma.data.dto.field.SalaryDto
import ru.practicum.android.diploma.data.dto.field.ScheduleDto

data class VacancyDetailedDto(
    val id: String,
    val area: AreaDto?,
    val contacts: ContactsDto?,
    val description: String?,
    val employer: EmployerDto?,
    val employment: EmploymentDto,
    val experienceId: String?,
    val experience: ExperienceDto,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillsDto>?,
    val name: String,
    val salary: SalaryDto?,
    val schedule: ScheduleDto?,
)
