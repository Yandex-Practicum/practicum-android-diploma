package ru.practicum.android.diploma.common.data.dto.allregions

import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.filter.data.dto.model.AreaDto

data class RegionsResponse(
    val result: List<AreaDto>
) : Response()
