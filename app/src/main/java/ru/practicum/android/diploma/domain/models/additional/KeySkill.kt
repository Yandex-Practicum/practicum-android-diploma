package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeySkill(
    val name: String
) : Parcelable
