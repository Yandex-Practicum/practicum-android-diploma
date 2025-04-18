package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: String? = "",
    val gross: Boolean? = true
) : Parcelable
