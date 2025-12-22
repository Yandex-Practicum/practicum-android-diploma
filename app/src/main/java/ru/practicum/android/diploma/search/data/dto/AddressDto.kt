package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    val city: String,
    val street: String,
    val building: String,
    @SerializedName("raw")
    val fullAddress: String
)
