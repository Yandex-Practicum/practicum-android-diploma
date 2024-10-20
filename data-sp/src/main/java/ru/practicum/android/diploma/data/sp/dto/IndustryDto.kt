package ru.practicum.android.diploma.data.sp.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndustryDto(
    val id: String?,
    val name: String?,
) : Parcelable {
    companion object {
        fun emptyIndustryDto(): IndustryDto {
            return IndustryDto(null, null)
        }
    }
}
