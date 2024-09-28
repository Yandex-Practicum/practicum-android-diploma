package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class LogoScalable(
    val default: Default,
    val xs: Xs,
) : Parcelable
