package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    val number: String? = null,
    val city: String? = null,
    val country: String? = null
) : Parcelable
