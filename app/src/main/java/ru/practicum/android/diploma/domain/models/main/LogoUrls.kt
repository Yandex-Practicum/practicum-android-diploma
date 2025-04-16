package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoUrls(
    val logo90: String? = "",
    val logo240: String? = "",
    val original: String? = ""
) : Parcelable
