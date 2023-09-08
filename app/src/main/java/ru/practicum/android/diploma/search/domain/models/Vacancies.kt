package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.di.annotations.NewResponse

@NewResponse
data class Vacancies (
    val found: Int,
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val per_page: Int,
)