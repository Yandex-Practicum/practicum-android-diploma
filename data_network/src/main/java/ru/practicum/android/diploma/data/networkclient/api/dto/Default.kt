package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Default(
    val height: Int,
    val url: String,
    val width: Int,
) : Parcelable
