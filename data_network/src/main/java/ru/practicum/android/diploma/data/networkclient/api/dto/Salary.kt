package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Salary(
    val currency: String,
    val from: Int,
    val gross: Boolean,
    val to: Int?,
) : Parcelable
