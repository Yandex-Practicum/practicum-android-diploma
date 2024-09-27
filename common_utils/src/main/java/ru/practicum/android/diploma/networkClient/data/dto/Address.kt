package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val building: String,
    val city: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    val metro_stations: List<MetroStation>,
    val street: String,
) : Parcelable
