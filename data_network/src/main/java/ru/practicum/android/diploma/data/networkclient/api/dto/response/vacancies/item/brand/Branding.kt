package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.brand

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Branding(
    val tariff: String,
    val type: String,
) : Parcelable
