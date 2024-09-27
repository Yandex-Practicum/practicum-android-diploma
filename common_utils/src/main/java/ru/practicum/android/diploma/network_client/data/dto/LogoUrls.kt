package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoUrls(
    val `240`: String,
    val `90`: String,
    val original: String,
) : Parcelable
