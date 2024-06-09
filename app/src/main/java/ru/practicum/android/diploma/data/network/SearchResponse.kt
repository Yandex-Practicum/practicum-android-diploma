package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyDto

data class SearchResponse(val items: ArrayList<VacancyDto>?, val page: Int, val pages: Int, val found: Int) : Response()
