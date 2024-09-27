package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val building: String,
    val city: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    @SerializedName("metro_stations") val metroStations: List<MetroStation>,
    val street: String,
) : Parcelable
