package ru.practicum.android.diploma.commons.data.dto.detailed

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.commons.data.dto.detailed.field.AreaDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.ContactsDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.EmployerDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.EmploymentDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.ExperienceDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.KeySkillsDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.SalaryDto
import ru.practicum.android.diploma.commons.data.dto.detailed.field.ScheduleDto

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
