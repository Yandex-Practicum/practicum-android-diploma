package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClusterGroup(
    val id: String,
    val name: String,
) : Parcelable
