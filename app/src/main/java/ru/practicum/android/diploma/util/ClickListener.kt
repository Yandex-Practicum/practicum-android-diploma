package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.VacancySearch

interface ClickListener {
    fun onVacancyClick(item: VacancySearch)
}
