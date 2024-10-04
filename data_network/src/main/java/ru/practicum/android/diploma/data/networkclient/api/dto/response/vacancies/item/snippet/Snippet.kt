package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.snippet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Snippet(
    val requirement: String,
    val responsibility: String,
) : Parcelable
