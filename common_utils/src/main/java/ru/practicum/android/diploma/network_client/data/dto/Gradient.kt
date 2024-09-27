package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gradient(
    val angle: Int,
    val color_list: List<Color>,
) : Parcelable
