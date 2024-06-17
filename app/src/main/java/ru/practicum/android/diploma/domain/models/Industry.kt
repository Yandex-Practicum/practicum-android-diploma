package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Industry(
    val id: String,
    val name: String,
    var isSelected: Boolean
) : Parcelable
