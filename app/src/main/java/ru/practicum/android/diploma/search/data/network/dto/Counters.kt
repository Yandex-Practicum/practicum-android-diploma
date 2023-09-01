package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Counters(
    val responses: Int = 0,
)