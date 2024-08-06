package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.components.Address
import ru.practicum.android.diploma.data.components.Area
import ru.practicum.android.diploma.data.components.Contacts
import ru.practicum.android.diploma.data.components.Employer
import ru.practicum.android.diploma.data.components.Employment
import ru.practicum.android.diploma.data.components.Experience
import ru.practicum.android.diploma.data.components.KeySkill
import ru.practicum.android.diploma.data.components.Salary
import ru.practicum.android.diploma.data.components.Schedule

data class VacancyResponse(
    val id: Int,
    val name: String,
    val employer: Employer,
    val salary: Salary?,
    val area: Area,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    val description: String,
    val employment: Employment?,
    val experience: Experience?,
    val schedule: Schedule?,
    val contacts: Contacts?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkill>,
    val address: Address?
) : Responce()
