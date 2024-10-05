package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.VacancySearch

fun interface ClickListener {
    fun onClick(item: VacancySearch)
}
