package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Address(
    val building: String? = "",
    val city: String? = "",
    val description: String? = "",
    @SerialName("metro_stations") val metroStations: List<MetroStation>? = emptyList(),
    val street: String? = ""
) : Parcelable
