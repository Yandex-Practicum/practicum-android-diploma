package ru.practicum.android.diploma.favorite.presintation

import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

sealed class FavoriteScreenState {
    data class ContentState(val vacancies: List<Vacancy>) : FavoriteScreenState()
    object EmptyState : FavoriteScreenState()
    object ErrorState : FavoriteScreenState()
}
