package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.brand.media

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureScalable(
    val default: Default,
    val xs: Xs,
) : Parcelable
