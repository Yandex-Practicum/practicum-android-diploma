package ru.practicum.android.diploma.presentation.filtration.effect

import ru.practicum.android.diploma.domain.models.FilterIndustry

sealed interface FiltrationEffect {
    data class NavigateBack(val filters: Filters) : FiltrationEffect
    data object OpenIndustriesScreen : FiltrationEffect
}

data class Filters(
    val salary: Int?,
    val onlyWithSalary: Boolean? = false,
    val industry: FilterIndustry?,
)
