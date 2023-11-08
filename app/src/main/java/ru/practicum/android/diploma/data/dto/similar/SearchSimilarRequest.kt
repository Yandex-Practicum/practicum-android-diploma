package ru.practicum.android.diploma.data.dto.similar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchSimilarRequest(val id: String) : Parcelable