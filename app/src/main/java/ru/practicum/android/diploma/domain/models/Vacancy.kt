package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.practicum.android.diploma.domain.models.`object`.Area
import ru.practicum.android.diploma.domain.models.`object`.ClusterGroup
import ru.practicum.android.diploma.domain.models.`object`.Contacts
import ru.practicum.android.diploma.domain.models.`object`.Employment
import ru.practicum.android.diploma.domain.models.`object`.Experience
import ru.practicum.android.diploma.domain.models.`object`.KeySkills
import ru.practicum.android.diploma.domain.models.`object`.Salary
import ru.practicum.android.diploma.domain.models.`object`.Schedule


@Parcelize
data class Vacancy(
    val argument: String,
    val disable_url:String,
    val cluster_group: ClusterGroup,
    val value: String,
    val value_description: String

): Parcelable
