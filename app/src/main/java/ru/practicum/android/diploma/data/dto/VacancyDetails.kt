package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetails(
    val id: String,
    val name: String,

    val area: Area? = Area(),

    val description: String? = null,

    val employer: Employer? = null,

    @SerializedName("key_skills") val keySkills: ArrayList<KeySkill> = arrayListOf(),

    val salary: Salary? = null,
    val salaryRange: Salary? = null,

    val experience: Experience? = null,
)
