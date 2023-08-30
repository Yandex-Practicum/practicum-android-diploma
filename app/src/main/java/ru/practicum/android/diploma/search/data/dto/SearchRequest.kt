package ru.practicum.android.diploma.search.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchRequest(val expression: String) : Parcelable
