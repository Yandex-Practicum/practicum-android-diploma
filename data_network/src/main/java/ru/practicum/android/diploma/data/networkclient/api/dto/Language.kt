package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Language(
    val id: String,
    val level: Level,
    val name: String,
) : Parcelable
