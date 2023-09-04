package ru.practicum.android.diploma.search.data.network.dto.response

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.dto.RegionDto


@Serializable
class RegionCodeResponse (
    var results: List<RegionDto>
) : CodeResponse(){
}