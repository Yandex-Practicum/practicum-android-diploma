package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    @SerializedName("id")
    val vacancyId: Long,
    val name: String?,
    val area: AreaDto?,
    val employer: EmployerDto?,
    val salary: SalaryDto?,
    val experience: ExperienceDto?,
    @SerializedName("employment_form")
    val employmentForm: EmploymentFormDto?,
    val schedule: ScheduleDto?
)
