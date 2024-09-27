package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaX(
    val areas: List<Area>,
    val id: String,
    val name: String,
    val parent_id: String,
) : Parcelable
