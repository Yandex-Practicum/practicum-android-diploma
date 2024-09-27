package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val id: String,
    val level: Level,
    val name: String,
) : Parcelable
