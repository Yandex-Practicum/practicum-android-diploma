package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Manager(
    val id: String,
) : Parcelable
