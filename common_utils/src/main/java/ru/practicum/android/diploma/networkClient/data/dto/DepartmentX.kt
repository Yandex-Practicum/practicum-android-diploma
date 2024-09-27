package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DepartmentX(
    val id: String,
    val name: String,
) : Parcelable
