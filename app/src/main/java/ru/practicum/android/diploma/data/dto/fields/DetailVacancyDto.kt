package ru.practicum.android.diploma.data.dto.fields

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.network.Response

data class DetailVacancyDto(
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
) : Response()
