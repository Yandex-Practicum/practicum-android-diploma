package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoUrls(
    @SerializedName("240") val deg240: String,
    @SerializedName("90") val deg90: String,
    val original: String,
) : Parcelable
