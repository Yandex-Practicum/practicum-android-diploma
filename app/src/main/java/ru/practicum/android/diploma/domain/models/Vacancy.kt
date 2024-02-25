package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vacancy(
    val id: String,
    val name: String,
    val city: String?,
    val employer: String?,
    val employerLogoUrls: String?,
    val currency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

}
