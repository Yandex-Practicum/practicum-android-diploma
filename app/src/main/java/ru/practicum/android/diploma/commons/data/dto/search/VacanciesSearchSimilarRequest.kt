package ru.practicum.android.diploma.commons.data.dto.search

import ru.practicum.android.diploma.commons.data.Constant.PER_PAGE_ITEMS

data class VacanciesSearchSimilarRequest(
    val id: String,
    val page: Long,
    val amount: Long = PER_PAGE_ITEMS
)
