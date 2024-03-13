package ru.practicum.android.diploma.domain.filter.datashared

import kotlinx.serialization.Serializable

@Serializable
data class RegionShared(
    val regionId: String?,
    val regionParentId: String?,
    val regionName: String?
)
