package ru.practicum.android.diploma.data.sp.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterDto(
    val placeDto: PlaceDto?,
    val branchOfProfession: String?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
) : Parcelable
