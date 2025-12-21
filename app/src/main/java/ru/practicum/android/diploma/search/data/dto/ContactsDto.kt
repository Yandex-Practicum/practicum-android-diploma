package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    @SerializedName("phones")
    val phones: List<PhoneDto>
)
