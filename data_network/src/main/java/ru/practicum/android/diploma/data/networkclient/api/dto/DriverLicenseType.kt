package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class DriverLicenseType(
    val id: String,
) : Parcelable
