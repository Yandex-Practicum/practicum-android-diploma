package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class MetroStation(
    @SerialName("station_id") val stationId: String? = "",
    @SerialName("station_name") val stationName: String? = ""
) : Parcelable
