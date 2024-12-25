package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val includedRegions: List<Country>,
    val id: String?,
    val name: String?,
    val parentId: String? = null
) : Parcelable
