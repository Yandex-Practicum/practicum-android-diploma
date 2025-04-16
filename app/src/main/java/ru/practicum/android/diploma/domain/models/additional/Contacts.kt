package ru.practicum.android.diploma.domain.models.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contacts(
    val name: String? = null,
    val email: String? = null,
    val phones: List<Phone> = emptyList()
) : Parcelable
