package ru.practicum.android.diploma.cache.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryCache(
    val idCountry: String,
    val nameCountry: String,
    val regions: List<RegionCache>
) : Parcelable
