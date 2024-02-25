package ru.practicum.android.diploma.data.response

import ru.practicum.android.diploma.data.search.network.Response
import ru.practicum.android.diploma.domain.models.Countries

data class CountryResponse(val results: List<Countries>) : Response()
