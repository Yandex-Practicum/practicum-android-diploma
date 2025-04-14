package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.KeySkill
import ru.practicum.android.diploma.data.dto.Salary

data class GetVacancyDetailsResponse(
    val id: String,
    val name: String,

    val area: Area? = Area(),

    val description: String? = null,

    val employer: Employer? = null,

    @SerializedName("key_skills") val keySkills: ArrayList<KeySkill> = arrayListOf(),

    val salary: Salary? = null,
    val salaryRange: Salary? = null,

    val experience: Experience? = null,
) : ApiResponse()
