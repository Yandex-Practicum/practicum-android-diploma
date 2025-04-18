package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employer(
    val id: String = "",
    val name: String = "",
    val logoUrls: LogoUrls? = null,
    val url: String = "",
    val trusted: Boolean = false
) : Parcelable
