package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.domain.models.additional.Schedule

@Parcelize
data class VacancyShort(
    val postedAt: String,
    val vacancyId: String,
    val logoUrl: LogoUrls?,
    val name: String,
    val area: String,
    val employer: String,
    val salary: Salary? = null,
    val schedule: Schedule? = null,
    val industries: List<Industry> = emptyList()
) : Parcelable
