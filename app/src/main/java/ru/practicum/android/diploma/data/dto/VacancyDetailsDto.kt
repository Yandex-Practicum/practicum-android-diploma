package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetailsDto(
    val id: String,
    val name: String,

    val area: AreaDto? = AreaDto(),

    val description: String? = null,

    val employer: EmployerDto? = null,

    @SerializedName("key_skills") val keySkills: List<KeySkillDto> = arrayListOf(),

    val salary: SalaryDto? = null,
    val salaryRange: SalaryDto? = null,

    val experience: ExperienceDto? = null
)
