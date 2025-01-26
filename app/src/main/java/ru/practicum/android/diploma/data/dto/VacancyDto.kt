package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    val name: String,
    val area: AreaDto,
    val salary: SalaryDto?,
    val employer: EmployerDto,
    @SerializedName("employment_form")
    val employment: EmploymentDto?,
    val experience: ExperienceDto?,
    val description: String?,
    @SerializedName("alternate_url")
    val alternateUrl: String
)
