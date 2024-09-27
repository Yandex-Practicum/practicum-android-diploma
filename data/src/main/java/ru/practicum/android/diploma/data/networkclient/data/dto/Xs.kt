package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Xs(
    val height: Int,
    val url: String,
    val width: Int,
) : Parcelable
