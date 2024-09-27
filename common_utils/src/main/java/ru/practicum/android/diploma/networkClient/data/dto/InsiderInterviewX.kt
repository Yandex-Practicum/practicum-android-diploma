package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsiderInterviewX(
    val id: String,
    val url: String
) : Parcelable
