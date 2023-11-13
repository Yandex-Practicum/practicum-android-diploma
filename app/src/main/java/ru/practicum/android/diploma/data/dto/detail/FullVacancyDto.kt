package ru.practicum.android.diploma.data.dto.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Contacts
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.EmploymentType
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.Language
import ru.practicum.android.diploma.data.dto.License
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.Salary
import ru.practicum.android.diploma.data.dto.SkillName

@Parcelize
data class FullVacancyDto(
    val id: String,
    val name: String,
    val area: Area,
    val alternate_url: String?,
    val brandedDescription: String?,
    val contacts: Contacts?,
    val description: String?,
    val employer: Employer?,
    val experience: Experience?,
    val employment: EmploymentType?,
    val salary: Salary?,
    val keySkills: List<SkillName>?,
    val driverLicense: List<License>?,
    val languages: List<Language>?,
) : Parcelable, Response()
