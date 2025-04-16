package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    val number: String? = "",
    val city: String? = "",
    val country: String? = ""
) : Parcelable
