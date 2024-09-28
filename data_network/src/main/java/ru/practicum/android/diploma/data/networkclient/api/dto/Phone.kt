package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Phone(
    val city: String,
    val comment: String?,
    val country: String,
    val number: String,
) : Parcelable
