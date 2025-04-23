package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.SerialName

@Serializable
data class MetroStationDto(
    @SerialName("station_id") val stationId: String? = null,
    @SerialName("station_name") val stationName: String? = null
)
