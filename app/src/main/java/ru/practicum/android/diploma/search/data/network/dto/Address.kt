package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val building: String = "",
    val city: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val metro_stations: List<MetroStation> = emptyList(),
    val street: String = "",
)