package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Vacancy(
    val id: String,
    val name: String,
    val employer: Employer? = null,
    val salary: Salary? = null,

    @SerializedName("salary_range")
    val salaryRange: Salary? = null,
)
