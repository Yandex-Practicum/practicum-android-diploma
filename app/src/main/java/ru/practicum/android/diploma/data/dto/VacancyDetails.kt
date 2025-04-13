package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDetails(
    var id: String,
    var name: String,

    var area: Area? = Area(),

    var description: String? = null,

    var employer: Employer? = null,

    @SerializedName("key_skills") var keySkills: ArrayList<KeySkill> = arrayListOf(),

    var salary: Salary? = null,
    var salaryRange: Salary? = null,

    var experience: Experience? = null,
)
