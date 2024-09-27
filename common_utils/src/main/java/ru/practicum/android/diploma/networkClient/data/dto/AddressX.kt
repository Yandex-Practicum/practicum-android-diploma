package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressX(
    val building: String,
    val city: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    val metro_stations: List<MetroStationX>,
    val street: String,
) : Parcelable
