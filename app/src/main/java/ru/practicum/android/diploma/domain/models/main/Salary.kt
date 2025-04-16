package ru.practicum.android.diploma.domain.models.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Salary(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String? = null,
    val gross: Boolean? = null
) : Parcelable
