package ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaInVacancy(
    val id: String,
    val name: String,
    val url: String?,
) : Parcelable
