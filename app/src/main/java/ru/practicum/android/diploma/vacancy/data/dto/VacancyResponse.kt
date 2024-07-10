package ru.practicum.android.diploma.vacancy.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.data.dto.Resp
import ru.practicum.android.diploma.search.data.dto.components.Area
import ru.practicum.android.diploma.search.data.dto.components.Employer
import ru.practicum.android.diploma.search.data.dto.components.Salary
import ru.practicum.android.diploma.vacancy.data.dto.components.Contacts
import ru.practicum.android.diploma.vacancy.data.dto.components.Employment
import ru.practicum.android.diploma.vacancy.data.dto.components.Experience
import ru.practicum.android.diploma.vacancy.data.dto.components.Schedule

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
    val schedule: Schedule,
    val contacts: Contacts?
) : Resp()
