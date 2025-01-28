package ru.practicum.android.diploma.search.presentation.items

import ru.practicum.android.diploma.search.domain.model.Salary

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
