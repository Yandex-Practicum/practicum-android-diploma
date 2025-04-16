package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contacts(
    val name: String? = "",
    val email: String? = "",
    val phones: List<Phone> = emptyList()
) : Parcelable
