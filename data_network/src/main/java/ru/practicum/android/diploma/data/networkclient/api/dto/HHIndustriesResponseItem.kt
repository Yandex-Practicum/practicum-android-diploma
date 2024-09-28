package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class HHIndustriesResponseItem(
    val id: String,
    val industries: List<Industry>,
    val name: String,
) : Parcelable
