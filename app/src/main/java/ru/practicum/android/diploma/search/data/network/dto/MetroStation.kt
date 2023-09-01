package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetroStation(
    val lat: Double? = 0.0,
    val line_id: String? = "",
    val line_name: String? = "",
    val lng: Double? = 0.0,
    val station_id: String? = "",
    val station_name: String? = "",
)