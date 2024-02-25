package ru.practicum.android.diploma.data.dto.response

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.domain.models.Countries

data class CountryResponse(val results: List<Countries>) : Response()
