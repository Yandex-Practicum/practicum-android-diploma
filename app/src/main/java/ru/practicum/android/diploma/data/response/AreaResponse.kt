package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.domain.models.Area

data class AreaResponse(val results: List<Area>) : Response()
