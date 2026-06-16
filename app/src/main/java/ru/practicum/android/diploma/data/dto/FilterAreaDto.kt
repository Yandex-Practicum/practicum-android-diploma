package ru.practicum.android.diploma.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
) : Parcelable
