package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoUrls(
    val logo90: String? = null,
    val logo240: String? = null,
    val original: String? = null
) : Parcelable
