package ru.practicum.android.diploma.networkClient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrandSnippet(
    val background: Background,
    val logo: String,
    val logo_scalable: LogoScalable,
    val logo_xs: String,
    val picture: String,
    val picture_scalable: PictureScalable,
    val picture_xs: String,
) : Parcelable
