package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureScalable(
    val default: Default,
    val xs: Xs,
) : Parcelable
