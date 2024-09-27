package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureScalable(
    val default: ru.practicum.android.diploma.data.networkclient.data.dto.Default,
    val xs: ru.practicum.android.diploma.data.networkclient.data.dto.Xs,
) : Parcelable
