package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Default(
    val height: Int,
    val url: String,
    val width: Int,
) : Parcelable
