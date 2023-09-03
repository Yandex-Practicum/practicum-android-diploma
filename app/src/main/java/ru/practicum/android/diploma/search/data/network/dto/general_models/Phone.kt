package ru.practicum.android.diploma.search.data.network.dto.general_models


import kotlinx.serialization.Serializable

@Serializable
data class Phone(
    val city: String? = "",
    val comment: String? = "",
    val country: String? = "",
    val number: String? = "",
)