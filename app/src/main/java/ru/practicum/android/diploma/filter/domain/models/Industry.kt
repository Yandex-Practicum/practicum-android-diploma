package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Industry (
    val id: String,
    val industries: List<IndustryArea>,
    val name: String,
): Parcelable