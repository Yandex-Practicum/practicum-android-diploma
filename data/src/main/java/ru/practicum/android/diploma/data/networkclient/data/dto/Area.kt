package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Area(
    val id: String,
    val name: String,
    val url: String,
) : Parcelable
