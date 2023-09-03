package ru.practicum.android.diploma.favourite.presentation.models

import ru.practicum.android.diploma.search.domain.models.Vacancy


sealed interface FavoriteStateInterface {
    object FavoriteVacanciesIsEmpty : FavoriteStateInterface

    data class FavoriteVacancies(
        val favoriteVacancies: List<Vacancy>,
    ) : FavoriteStateInterface
}