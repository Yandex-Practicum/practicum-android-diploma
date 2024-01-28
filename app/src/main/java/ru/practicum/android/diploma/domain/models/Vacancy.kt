package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.practicum.android.diploma.domain.models.`object`.ClusterGroup

@Parcelize
data class Vacancy(
    val argument: String,
    val disableUrl: String,
    val clusterGroup: ClusterGroup,
    val value: String,
    val valueDescription: String

) : Parcelable
