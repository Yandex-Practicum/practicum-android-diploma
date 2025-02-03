package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.common.sharedprefs.models.Filter

data class SearchQueryParams(
    val text: String, // строка ввода
    val page: Int = 0, // Страница списка
    // в следующих эпиках добавить сюда фильтры
    val filter: Filter? = null
)
