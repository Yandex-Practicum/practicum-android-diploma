package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(
    val id: String,
    val level: ru.practicum.android.diploma.data.networkclient.data.dto.Level,
    val name: String,
) : Parcelable
