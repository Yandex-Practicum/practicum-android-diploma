package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HHIndustriesResponseItem(
    val id: String,
    val industries: List<Industry>,
    val name: String,
) : Parcelable
