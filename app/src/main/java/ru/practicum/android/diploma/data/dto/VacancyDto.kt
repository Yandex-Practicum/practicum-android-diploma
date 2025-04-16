package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    val name: String,
    val employer: EmployerDto? = null,
    val salary: SalaryDto? = null,

    @SerializedName("salary_range")
    val salaryRange: SalaryDto? = null
)
