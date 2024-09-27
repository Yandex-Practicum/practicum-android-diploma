package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Argument(
    val argument: String,
    val cluster_group: ClusterGroup,
    val disable_url: String,
    val hex_color: String,
    val metro_type: String,
    val value: String,
    val value_description: String,
) : Parcelable
