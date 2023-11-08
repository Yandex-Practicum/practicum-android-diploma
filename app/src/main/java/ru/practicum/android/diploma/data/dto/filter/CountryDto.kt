package ru.practicum.android.diploma.data.dto.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDto(
    val url: String,
    val id: String,
    val name: String
) : Parcelable
