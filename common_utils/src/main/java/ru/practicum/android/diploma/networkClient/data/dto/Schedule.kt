package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val id: String,
    val name: String,
) : Parcelable
