package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoScalable(
    val default: Default,
    val xs: Xs
) : Parcelable
