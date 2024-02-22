package ru.practicum.android.diploma.data.dto.request

import ru.practicum.android.diploma.util.Constant.PER_PAGE_ITEMS

data class VacanciesSearchSimilarRequest(
    val id: String,
    val page: Long,
    val amount: Long = PER_PAGE_ITEMS
)
