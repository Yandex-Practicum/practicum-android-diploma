package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import ru.practicum.android.diploma.search.domain.model.VacancyDetail

data class VacancyDetailsState(
    val vacancy: VacancyDetail? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
