package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.filter.data.model.RegionDto


@Serializable
data class RegionCodeResponse (
    val areas: List<RegionDto?>? = null
) : CodeResponse()