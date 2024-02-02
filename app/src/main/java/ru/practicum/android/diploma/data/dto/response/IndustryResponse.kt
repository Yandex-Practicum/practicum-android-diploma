package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.domain.models.Industry

class IndustryResponse(val results: List<Industry>) : Response()
