package ru.practicum.android.diploma.commons.data.dto.search

import ru.practicum.android.diploma.commons.data.Constant.PER_PAGE_ITEMS

data class VacanciesSearchByNameRequest(
    val name: String,
    val page: Long,
    val amount: Int = PER_PAGE_ITEMS
)
