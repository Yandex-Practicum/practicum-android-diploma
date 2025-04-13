package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class Vacancy(
    val id: String,
    var name: String,
    var employer: Employer? = null,
    var salary: Salary? = null,

    @SerializedName("salary_range")
    var salaryRange: Salary? = null,
)
