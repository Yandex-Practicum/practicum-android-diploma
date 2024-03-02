package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubIndustry(
    val id: String,
    val name: String
): Parcelable
