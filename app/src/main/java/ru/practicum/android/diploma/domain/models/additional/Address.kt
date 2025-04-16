package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Address(
    val building: String? = null,
    val city: String? = null,
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("metro_stations") val metroStations: List<MetroStation>? = null,
    val street: String? = null
) : Parcelable
