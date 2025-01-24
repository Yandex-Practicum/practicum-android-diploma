package ru.practicum.android.diploma.search.presentation.list_items

import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.domain.model.VacancyList

sealed interface ListItem {

    data class Vacancy(
        val id: String,
        val name: String,
        val areaName: String,
        val employer: String,
        val iconUrl: String? = null,
        val salary: Salary? = null,
    ) : ListItem

    data object LoadingItem : ListItem
}
