package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.experience

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Experience(
    val id: String,
    val name: String,
) : Parcelable
