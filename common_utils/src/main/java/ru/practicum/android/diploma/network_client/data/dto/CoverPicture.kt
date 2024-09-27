package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPicture(
    val resized_height: Int,
    val resized_path: String,
    val resized_width: Int,
) : Parcelable
