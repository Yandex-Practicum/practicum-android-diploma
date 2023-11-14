package ru.practicum.android.diploma.data.dto.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.data.dto.Response

@Parcelize

data class IndustryDto (
        val url: String,
        val id: String,
        val name: String
) : Parcelable, Response()

