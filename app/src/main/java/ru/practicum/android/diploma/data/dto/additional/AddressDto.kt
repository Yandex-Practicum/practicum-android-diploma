package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val building: String? = null,
    val city: String? = null,
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("metro_stations") val metroStations: List<MetroStationDto>? = null,
    val street: String? = null
)
