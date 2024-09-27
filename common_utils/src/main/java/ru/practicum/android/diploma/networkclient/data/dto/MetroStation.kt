package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetroStation(
    val lat: Double,
    @SerializedName("line_id") val lineId: String,
    @SerializedName("line_name") val lineName: String,
    val lng: Double,
    @SerializedName("station_id") val stationId: String,
    @SerializedName("station_name") val stationName: String,
) : Parcelable
