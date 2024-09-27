package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Background(
    val color: String?,
    val gradient: Gradient,
) : Parcelable
