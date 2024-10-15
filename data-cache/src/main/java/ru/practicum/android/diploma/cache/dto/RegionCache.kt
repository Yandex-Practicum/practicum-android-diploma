package ru.practicum.android.diploma.cache.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegionCache(
    val idCountry: String,
    val idRegion: String,
    val nameRegion: String,
) : Parcelable
