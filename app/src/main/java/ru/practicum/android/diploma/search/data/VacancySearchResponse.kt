package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.util.network.Response
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDto

class VacancySearchResponse(
    val searchResults: List<VacancyDto>
) : Response()
