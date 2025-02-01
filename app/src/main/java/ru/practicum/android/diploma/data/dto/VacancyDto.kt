package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    @SerializedName("id")
    val vacancyId: Long,
    val name: String?,
    val area: VacancyAreaDto?,
    val employer: EmployerDto?,
    val salary: SalaryDto?,
    val experience: ExperienceDto?,
    @SerializedName("employment_form")
    val employmentForm: EmploymentFormDto?,
    val employment: EmploymentFormDto?, // deprecated
    val schedule: ScheduleDto?,
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<SkillDto?>,
    @SerializedName("alternate_url")
    val alternateUrl: String?,
    val address: AddressDto?
) : Response()
