package ru.practicum.android.diploma.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRequest(val expression: String) : Parcelable

@Parcelize
data class SearchRequestOptions(val options: HashMap<String, String>) : Parcelable