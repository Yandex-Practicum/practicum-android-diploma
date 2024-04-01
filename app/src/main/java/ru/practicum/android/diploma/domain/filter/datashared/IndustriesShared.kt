package ru.practicum.android.diploma.domain.filter.datashared

import kotlinx.serialization.Serializable

@Serializable
data class IndustriesShared(
    val industriesId: String?,
    val industriesName: String?
)
