package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedFilter(
    val country: Country? = null,
    val region: Region? = null,
    val industry: Industry? = null,
    val salary: String? = null,
    val visibility: Boolean? = null,
) : Parcelable {
    companion object { val empty = SelectedFilter() }
}