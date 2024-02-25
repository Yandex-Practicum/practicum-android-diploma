package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.domain.models.Areas

class RegionResponse(val results: List<Areas>) : Response()
