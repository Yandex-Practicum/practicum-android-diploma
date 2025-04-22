package ru.practicum.android.diploma.presentation.favorites

import ru.practicum.android.diploma.domain.models.main.VacancyShort

sealed interface FavoriteVacanciesState {
    data object Loading : FavoriteVacanciesState

    data object Empty : FavoriteVacanciesState

    data object Error : FavoriteVacanciesState

    data class Content(
        val vacancies: List<VacancyShort>
    ) : FavoriteVacanciesState
}
