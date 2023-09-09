package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.filter.data.model.IndustryDto


@Parcelize
@Serializable
data class Industry (
    val id: String,
    val industries: List<Industry>,
    val name: String,
): Parcelable