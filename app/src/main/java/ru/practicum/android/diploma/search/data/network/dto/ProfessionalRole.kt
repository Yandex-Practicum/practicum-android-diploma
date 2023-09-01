package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalRole(
    val id: String = "",
    val name: String = "",
)