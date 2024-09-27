package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Snippet(
    val requirement: String,
    val responsibility: String,
) : Parcelable
