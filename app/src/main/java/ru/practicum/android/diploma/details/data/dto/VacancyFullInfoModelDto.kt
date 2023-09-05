package ru.practicum.android.diploma.details.data.dto

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.details.data.dto.assistants.ContactsDto
import ru.practicum.android.diploma.details.data.dto.assistants.EmploymentDto
import ru.practicum.android.diploma.details.data.dto.assistants.ExperienceDto
import ru.practicum.android.diploma.details.data.dto.assistants.KeySkillDto
import ru.practicum.android.diploma.details.data.dto.assistants.ScheduleDto
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.dto.general_models.Area
import ru.practicum.android.diploma.search.data.network.dto.general_models.Employer
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary

@Serializable
data class VacancyFullInfoModelDto(
    val id: String,
    val experience: ExperienceDto?,
    val employment: EmploymentDto?,
    val schedule: ScheduleDto?,
    val description: String?,
    val key_skills: List<KeySkillDto>?,
    val contacts: ContactsDto?,
    val area: Area?,
    val salary: Salary?,
    val name: String?,
    val employer: Employer?,
    val alternate_url: String?,
    ) : CodeResponse()