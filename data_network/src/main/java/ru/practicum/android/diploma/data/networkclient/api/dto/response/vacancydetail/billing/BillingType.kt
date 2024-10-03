package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.billing

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillingType(
    val id: String,
    val name: String,
) : Parcelable
