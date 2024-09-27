package ru.practicum.android.diploma.data.networkclient.rest_api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contacts(
    val email: String,
    val name: String,
    val phones: List<Phone>,
) : Parcelable
