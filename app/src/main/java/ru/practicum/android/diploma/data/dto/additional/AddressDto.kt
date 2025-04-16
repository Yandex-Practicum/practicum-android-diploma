package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val building: String?,
    val city: String?,
    val description: String?,
    @SerialName("metro_stations") val metroStations: List<MetroStationDto>?,
    val street: String?
)
