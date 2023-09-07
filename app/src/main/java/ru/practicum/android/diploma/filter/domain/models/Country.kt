package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.RegionArea

@Parcelize
@Serializable
data class Country(
    val id: String,
    val name: String,
): Parcelable