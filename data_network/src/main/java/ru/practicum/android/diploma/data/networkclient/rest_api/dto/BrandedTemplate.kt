package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrandedTemplate(
    val id: String,
    val name: String,
) : Parcelable
