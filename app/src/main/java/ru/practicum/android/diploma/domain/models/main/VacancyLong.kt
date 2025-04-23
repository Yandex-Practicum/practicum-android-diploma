package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.domain.models.additional.Address
import ru.practicum.android.diploma.domain.models.additional.Contacts
import ru.practicum.android.diploma.domain.models.additional.Employment
import ru.practicum.android.diploma.domain.models.additional.Experience
import ru.practicum.android.diploma.domain.models.additional.KeySkill
import ru.practicum.android.diploma.domain.models.additional.Schedule

@Parcelize
data class VacancyLong(
    val vacancyId: String = "",
    val name: String = "",
    val description: String = "",
    val salary: Salary? = null,
    val keySkills: List<KeySkill> = emptyList(),
    val areaName: String = "",
    val logoUrl: LogoUrls? = null,
    val employer: Employer? = null,
    val experience: Experience? = null,
    val employmentForm: Employment? = null,
    val schedule: Schedule? = null,
    val contacts: Contacts? = null,
    val address: Address? = null,
    val industries: List<Industry> = emptyList(),
    val publishedAt: String = "",
    val createdAt: String = "",
    val archived: Boolean? = null,
    val url: String = ""
) : Parcelable
