package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrandSnippet(
    val background: Background,
    val logo: String,
    @SerializedName("logo_scalable") val logoScalable: LogoScalable,
    @SerializedName("logo_xs") val logoXs: String,
    val picture: String,
    @SerializedName("picture_scalable") val pictureScalable: PictureScalable,
    @SerializedName("picture_xs") val pictureXs: String,
) : Parcelable
