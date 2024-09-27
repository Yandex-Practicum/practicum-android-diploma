package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Branding(
    val tariff: String,
    val type: String,
) : Parcelable
