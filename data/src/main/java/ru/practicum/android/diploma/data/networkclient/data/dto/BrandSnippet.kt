package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrandSnippet(
    val background: ru.practicum.android.diploma.data.networkclient.data.dto.Background,
    val logo: String,
    @SerializedName("logo_scalable") val logoScalable: ru.practicum.android.diploma.data.networkclient.data.dto.LogoScalable,
    @SerializedName("logo_xs") val logoXs: String,
    val picture: String,
    @SerializedName("picture_scalable") val pictureScalable: ru.practicum.android.diploma.data.networkclient.data.dto.PictureScalable,
    @SerializedName("picture_xs") val pictureXs: String,
) : Parcelable
