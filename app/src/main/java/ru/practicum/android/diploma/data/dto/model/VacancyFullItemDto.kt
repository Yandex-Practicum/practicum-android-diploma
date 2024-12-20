package ru.practicum.android.diploma.data.dto.model

import com.google.gson.annotations.SerializedName

data class VacancyFullItemDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto,
    val address: AddressDto?,
    val area: AreaDto,
    val experience: ExperienceDto,
    val employment: EmploymentDto,
    val schedule: ScheduleDto,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillsDto>,
    val description: String,
)
