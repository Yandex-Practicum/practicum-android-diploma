package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

class ContactInfoDto(
    @SerializedName("name")
    val contactName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phones")
    val phones: List<PhoneNumberDto>?
)
