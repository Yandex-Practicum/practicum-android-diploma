package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.domain.models.Areas

class RegionResponse(val results: List<Areas>) : Response()
