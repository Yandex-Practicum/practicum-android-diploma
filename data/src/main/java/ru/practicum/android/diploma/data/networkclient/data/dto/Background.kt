package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Background(
    val color: String?,
    val gradient: ru.practicum.android.diploma.data.networkclient.data.dto.Gradient,
) : Parcelable
