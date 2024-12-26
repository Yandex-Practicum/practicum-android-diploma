package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Region(
    val includedRegions: List<Region>,
    val id: String?,
    val name: String?,
    val parentId: String? = null,
    var parentCountry: Country? = null
) : Parcelable