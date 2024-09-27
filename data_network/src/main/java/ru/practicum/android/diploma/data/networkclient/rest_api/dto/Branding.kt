package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Branding(
    val tariff: String,
    val type: String,
) : Parcelable
