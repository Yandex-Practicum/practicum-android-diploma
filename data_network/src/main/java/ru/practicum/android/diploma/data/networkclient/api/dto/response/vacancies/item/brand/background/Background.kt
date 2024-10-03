package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.brand.background

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Background(
    val color: String?,
    val gradient: Gradient,
) : Parcelable
