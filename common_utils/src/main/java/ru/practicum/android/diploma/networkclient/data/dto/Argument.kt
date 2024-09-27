package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Argument(
    val argument: String,
    @SerializedName("cluster_group") val clusterGroup: ClusterGroup,
    @SerializedName("disable_url") val disableUrl: String,
    val hex_color: String,
    @SerializedName("metro_type") val metroType: String,
    val value: String,
    @SerializedName("value_description") val valueDescription: String,
) : Parcelable
