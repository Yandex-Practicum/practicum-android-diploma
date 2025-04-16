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
    val vacancyId: String,
    val name: String,
    val description: String,
    val salary: String? = null,
    val keySkills: List<KeySkill> = emptyList(),
    val areaName: String,
    val logoUrl: LogoUrls?,
    val employer: Employer,
    val experience: Experience?,
    val employmentForm: Employment?,
    val schedule: Schedule,
    val contacts: Contacts? = null,
    val address: Address? = null,
    val industries: List<Industry> = emptyList(),
    val publishedAt: String,
    val createdAt: String,
    val archived: Boolean
) : Parcelable
