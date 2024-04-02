package ru.practicum.android.diploma.data.filter.country.response

import ru.practicum.android.diploma.data.filter.country.dto.AreaDtoResponse
import ru.practicum.android.diploma.data.vacancies.response.Response

data class AreasResponse(
    val area: List<AreaDtoResponse>
) : Response()
