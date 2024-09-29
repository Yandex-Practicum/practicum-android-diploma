package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Area(
    val id: String,
    val name: String,
    val url: String,
) : Parcelable
