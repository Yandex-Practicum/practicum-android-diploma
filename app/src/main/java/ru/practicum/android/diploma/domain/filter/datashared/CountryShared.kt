package ru.practicum.android.diploma.domain.filter.datashared

import kotlinx.serialization.Serializable

@Serializable
data class CountryShared(
    val countryId: String?,
    val countryName: String?
)
