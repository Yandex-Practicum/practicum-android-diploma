package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class SalaryDto(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String
)
