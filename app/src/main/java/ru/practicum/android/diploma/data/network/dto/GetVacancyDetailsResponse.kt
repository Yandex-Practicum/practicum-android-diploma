package ru.practicum.android.diploma.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.ApiResponse
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.KeySkill
import ru.practicum.android.diploma.data.dto.Salary

data class GetVacancyDetailsResponse(
    var id: String,
    var name: String,

    var area: Area? = Area(),

    var description: String? = null,

    var employer: Employer? = null,

    @SerializedName("key_skills") var keySkills: ArrayList<KeySkill> = arrayListOf(),

    var salary: Salary? = null,
    var salaryRange: Salary? = null,

    var experience: Experience? = null,
) : ApiResponse()
