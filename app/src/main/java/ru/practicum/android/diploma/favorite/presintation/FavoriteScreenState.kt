package ru.practicum.android.diploma.favorite.presintation

import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

sealed interface FavoriteScreenState {
    data class ContentState(val vacancies: List<Vacancy>) : FavoriteScreenState
    object Empty : FavoriteScreenState
    object Error : FavoriteScreenState
}
