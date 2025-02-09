package ru.practicum.android.diploma.vacancy.data.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.vacancy.data.dto.AdressDto
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.dto.ScheduleDto

data class VacancyDetailsResponse(
    val id: String,
    val name: String,
    val salary: Salary? = null,
    val employer: EmployerDto? = null,
    val area: AreaDto? = null,
    val address: AdressDto? = null,
    val experience: ExperienceDto? = null,
    val schedule: ScheduleDto? = null,
    val employment: EmploymentDto? = null,
    val description: String? = null,
    @SerializedName("alternate_url") val alternateUrl: String? = null,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>? = null,
) : Response()
