package ru.practicum.android.diploma.data.sp.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceDto(
    val idCountry: String?,
    val nameCountry: String?,
    val idRegion: String?,
    val nameRegion: String?,
) : Parcelable {
    companion object {
        fun emptyPlaceDto(): PlaceDto {
            return PlaceDto(null, null, null, null)
        }
    }
}
